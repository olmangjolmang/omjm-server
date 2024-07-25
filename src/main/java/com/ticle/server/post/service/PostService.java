package com.ticle.server.post.service;

import com.ticle.server.memo.domain.Memo;
import com.ticle.server.post.repository.MemoRepository;
import com.ticle.server.post.domain.type.PostSort;
import com.ticle.server.post.dto.*;
import com.ticle.server.user.domain.type.Category;
import com.ticle.server.post.domain.Post;
import com.ticle.server.post.repository.PostRepository;
import com.ticle.server.scrapped.domain.Scrapped;
import com.ticle.server.scrapped.repository.ScrappedRepository;
import com.ticle.server.user.domain.User;
import com.ticle.server.user.repository.UserRepository;
import com.ticle.server.user.jwt.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RequiredArgsConstructor
@Service

public class PostService {

    private final PostRepository postRepository;
    private final ScrappedRepository scrappedRepository;
    private final UserRepository userRepository;
    private final MemoRepository memoRepository;

    private static final int SIZE = 9; // 한 페이지에 보여질 객체 수

    public Page<PostResponse> getArticles(Category category, String keyword, PostSort orderBy, Integer page) {
        Sort sort = PostSort.getOrder(orderBy);
        Pageable pageable = PageRequest.of(page - 1, SIZE, sort);

        Page<Post> postPage = postRepository.findByKeywordAndCategory(category, keyword, pageable);

        return postPage.map(PostResponse::from);
    }


    @Qualifier("geminiRestTemplate")
    @Autowired
    private RestTemplate restTemplate;

    @Value("${gemini.api.url}")
    private String apiUrl;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    //postId로 조회한 특정 post 정보 리턴
    public Post findArticleById(Long id) {

        Optional<Post> optionalPost = postRepository.findById(id);
        Post post = optionalPost.orElseThrow(() -> new IllegalArgumentException("Post not found with ID: " + id));

        return post;
    }

    // 아티클 스크랩 유무 확인
    public boolean ArticleIsScrapped(long postId, CustomUserDetails customUserDetails) {
        if (customUserDetails == null) {
            throw new IllegalArgumentException("로그인 상태가 아닙니다.");
        }

        Long userId = customUserDetails.getUserId();
        userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("해당 id의 user 찾을 수 없음 id: " + userId)
        );


        Optional<Scrapped> existingScrap = scrappedRepository.findByUserIdAndPost_PostId(userId, postId);
        return existingScrap.isPresent();
    }

    //post id별 함께 읽으면 좋을 아티클 추천
    public List<Post> ArticleReadRecommend(Long id) {

        Optional<Post> optionalPost = postRepository.findById(id);
        Post post = optionalPost.orElseThrow(() -> new IllegalArgumentException("Post not found with ID: " + id));

        Category now_post_category = post.getCategory();

        // 같은 카테고리 post들의 id와 title
        List<PostIdTitleDto> same_category_posts = postRepository.findIdByCategory(now_post_category);
        System.out.println("원래의 same_category_posts = " + same_category_posts);

        Collections.shuffle(same_category_posts, new Random()); //무작위로 섞음
        System.out.println("랜덤 same_category_posts = " + same_category_posts);

        //비교 title의 개수는 20개까지
        if (same_category_posts.size() >= 20) {
            same_category_posts = same_category_posts.subList(0, 20);
        }

        String now_post_title = post.getTitle();

        // Gemini에 요청 전송
        String requestUrl = apiUrl + "?key=" + geminiApiKey;
        String prompt = "현재 기사의 제목은 " + now_post_title + " 이야. " +
                "다음은 기사의 리스트야. 리스트 안의 title과 비교해서 " +
                "현재의 기사 제목과 가장 연관 된 기사 3개의 id를 리스트로 추출해줘." +
                "단, 현재 기사의 게시글번호인 " + id + "는 절대 포함되면 안돼. 3개를 추출해." +
                "예시: [게시글번호,게시글번호,게시글번호]\n" +
                "위의 형식을 꼭 지켜서 배열로 출력해줘." +
                "다음은 기사의 리스트야. " + same_category_posts;

//        System.out.println(prompt);

        GeminiRequest request = new GeminiRequest(prompt);
        GeminiResponse response = restTemplate.postForObject(requestUrl, request, GeminiResponse.class);
        System.out.println("response = " + response);

        List<Post> recommendPosts = response.extractRecommendedPosts(response, postRepository);

        System.out.println("response = " + response);
        while (recommendPosts.isEmpty() || recommendPosts.size() == 0) {
            // 추천 포스트가 비어있을 경우 다시 요청
            System.out.println("recommendPost = " + recommendPosts);
            System.out.println("비어서 다시 요청");
            request = new GeminiRequest(prompt);
            response = restTemplate.postForObject(requestUrl, request, GeminiResponse.class);
            recommendPosts = response.extractRecommendedPosts(response, postRepository);
        }
        return recommendPosts;
    }

    public Object scrappedById(long id, CustomUserDetails customUserDetails) {

        // 게시물 조회
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 post 찾을 수 없음 id: " + id));

        Long userId = customUserDetails.getUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 id의 user 찾을 수 없음 id: " + userId));

        // 이미 스크랩했는지 확인
        Optional<Scrapped> existingScrap = scrappedRepository.findByUserIdAndPost_PostId(userId, post.getPostId());

        Scrapped scrap;

        if (existingScrap.isPresent()) {
            scrap = existingScrap.get();

            if ("SCRAPPED".equals(scrap.getStatus())) { // 이미 스크랩한 상태 -> 스크랩 취소
                scrap.changeToUnscrapped();
                post.decreaseScrapCount();
            } else { // 스크랩
                scrap.changeToScrapped();
                post.increaseScrapCount();
            }
        } else {
            // 새로운 스크랩 생성
            scrap = Scrapped.builder()
                    .post(post)
                    .user(user)
                    .status("SCRAPPED")
                    .build();
            post.increaseScrapCount(); // 새로운 스크랩 시에도 카운트 증가
        }

        // post와 scrap 저장
        postRepository.save(post);
        return scrappedRepository.save(scrap);
    }

    public Object writeMemo(long id, CustomUserDetails customUserDetails, String targetText, String content) {
        Long userId = customUserDetails.getUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 id의 user 찾을 수 없음 id: " + userId));

        // 같은 내용의 targetText-content 세트가 있는지 확인
        Memo existingMemo = memoRepository.findByUserAndTargetTextAndContent(user, targetText, content);

        if (existingMemo != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 동일한 메모가 존재합니다.");
        }
        // Post 객체를 찾을 때 예외 처리
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 post 찾을 수 없음 id: " + id));

        Memo memo = Memo.builder()
                .post(post)
                .user(user)
                .targetText(targetText)
                .content(content)
                .build();
        return memoRepository.save(memo);
    }

    boolean isValidResponse(GeminiResponse response) {

        //문제의 텍스트만 뽑음
        String responseText = response.getCandidates().get(0).getContent().getParts().get(0).getText();
        if (responseText.contains("'''") || responseText.contains("```")) {
            // 코드를 보기로 내어주는 경우에는 예외처리
            return false;
        }
        return true;
    }

    public List<QuizResponse> createQuiz(Long id) {

        Optional<Post> optionalPost = postRepository.findById(id);
        Post post = optionalPost.orElseThrow(() -> new IllegalArgumentException("Post not found with ID: " + id));
        String postTitle = post.getTitle();
        String content = post.getContent(); // 크롤링 완료시에 해당 코드로 진행.

        // Gemini에 요청 전송
        String requestUrl = apiUrl + "?key=" + geminiApiKey;
        String prompt = "제공하는 기사의 내용에서 A,B,C,D를 고르는 객관식 퀴즈 5개를 만들어줘. " +
                "텍스트 스타일 적용시키지 말고 텍스트만 리턴해" +
                "정답은 하나야" +
                "문제번호: 문제번호\n" +
                "문제: 문제내용\n" +
                "A: 보기내용\n" +
                "B: 보기내용\n" +
                "C: 보기내용\n" +
                "D: 보기내용\n" +
                "정답: \n" +
                "위의 형식을 꼭 지켜서 순서대로 출력해줘, (문제번호:, 문제:, A:,B:,C:,D:,정답: 텍스트가 무조건 포함되어야해." +
                "코드를 참고하는 예제 절대 주지마." +
                "다음은 기사의 내용이야." + content;

//        System.out.println(prompt);
        GeminiRequest request = new GeminiRequest(prompt);
        GeminiResponse response = restTemplate.postForObject(requestUrl, request, GeminiResponse.class);
        while (!isValidResponse(response)) { //만들어진 문제에 '보기 코드를 참고하여' 문제를 푸는 경우 예외처리
            request = new GeminiRequest(prompt);
            response = restTemplate.postForObject(requestUrl, request, GeminiResponse.class); //다시 문제 생성
        }

        List<QuizResponse> quizSet = response.formatQuiz(postTitle); // 리턴 형식 지정하는 함수
        System.out.println("quizSet = " + quizSet);
        return quizSet;

    }

}
