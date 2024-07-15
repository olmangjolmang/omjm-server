package com.ticle.server.post.service;

import com.ticle.server.memo.domain.Memo;
import com.ticle.server.mypage.repository.NoteRepository;
import com.ticle.server.post.dto.*;
import com.ticle.server.scrapped.dto.ScrappedDto;
import com.ticle.server.user.domain.type.Category;
import com.ticle.server.post.domain.Post;
import com.ticle.server.post.repository.PostRepository;
import com.ticle.server.scrapped.domain.Scrapped;
import com.ticle.server.scrapped.repository.ScrappedRepository;
import com.ticle.server.user.domain.User;
import com.ticle.server.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service

public class PostService {

    private final PostRepository postRepository;
    private final ScrappedRepository scrappedRepository;
    private final UserService userService;
    private final NoteRepository noteRepository;

    // 카테고리에 맞는 글 찾기
    public Page<PostResponse> findAllByCategory(String category, int page) {

        final int SIZE = 9; // 한 페이지에 보여질 객체 수

        Pageable pageable = PageRequest.of(page - 1, SIZE);
        Page<Post> postPage;

        if (category == null || category.isEmpty()) {
            // 모든 글 조회
            postPage = postRepository.findAll(pageable);
        } else {
            //카테고리에 맞는 글 조회
            postPage = postRepository.findByCategory(Category.valueOf(category), pageable);
        }
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
    public Post findById(long id) {

        Optional<Post> optionalPost = postRepository.findById(id);
        Post post = optionalPost.orElseThrow(() -> new IllegalArgumentException("Post not found with ID: " + id));

        String now_post_title = post.getTitle();
        List<PostIdTitleDto> alltitle = postRepository.findAllPostSummaries();

        // Gemini에 요청 전송
        String requestUrl = apiUrl + "?key=" + geminiApiKey;
        String prompt = "현재 기사의 제목은 " + now_post_title + " 이야. " +
                "다음은 기사의 리스트야. 리스트 안의 title과 비교해서 " +
                "현재의 기사 제목과 가장 연관 된 기사 3개의 id, title을 각각의 리스트로 추출해줘." +
                "단, 현재 기사는 제외한다." +
                "예: postId=[1,2,3], postTitle=[title1, title2, title3] "
                + alltitle;

        System.out.println(prompt);

        GeminiRequest request = new GeminiRequest(prompt);
        GeminiResponse response = restTemplate.postForObject(requestUrl, request, GeminiResponse.class);

        List recommendPost = response.formatRecommendPost(); // 리턴 형식 지정하는 함수
        post.setRecommendPost(recommendPost);

        return post;
    }


    public Object scrappedById(long id, UserDetails userDetails) {

        // 게시물 조회
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 post 찾을 수 없음 id: " + id));

        // getUsername에는 email이 들어있음. / email로 유저 찾고 id 찾도록 함.
        User user = userService.getLoginUserByEmail(userDetails.getUsername());
        Long userId = user.getId();
//            System.out.println("User ID: " + userId);

        // 이미 스크랩했는지 확인
        Optional<Scrapped> existingScrap = scrappedRepository.findByUserIdAndPost_PostId(userId, post.getPostId());
        if (existingScrap.isPresent()) {
            // 이미 스크랩한 상태라면 스크랩 취소
            existingScrap.get().changeToUnscrapped(); //status를 unscrapped로 변경
            scrappedRepository.delete(existingScrap.get());
            return ScrappedDto.from(existingScrap.get());
        }

        Scrapped scrapped = new Scrapped();
        scrapped.setPost(post);
        scrapped.setUser(user);
        scrapped.changeToScrapped(); //status를 scrapped로 변경

        return scrappedRepository.save(scrapped);
    }

    public Object writeMemo(long id, UserDetails userDetails, String targetText, String content) {

        // getUsername에는 email이 들어있음. / email로 유저 찾고 id 찾도록 함.
        User user = userService.getLoginUserByEmail(userDetails.getUsername());

        // 같은 내용의 targetText-content 세트가 있는지 확인
        Memo existingMemo = noteRepository.findByUserAndTargetTextAndContent(user, targetText, content);

        if (existingMemo != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 동일한 메모가 존재합니다.");
        }

        Memo memo = new Memo();
        memo.setPost(postRepository.findByPostId(id));
        memo.setUser(user);
        memo.setTargetText(targetText);
        memo.setContent(content);

        return noteRepository.save(memo);
    }
}
