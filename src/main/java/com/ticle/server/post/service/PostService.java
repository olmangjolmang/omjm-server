package com.ticle.server.post.service;

import com.ticle.server.memo.domain.Memo;
import com.ticle.server.mypage.repository.MemoRepository;
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
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service

public class PostService {

    private final PostRepository postRepository;
    private final ScrappedRepository scrappedRepository;
    private final UserService userService;
    private final MemoRepository memoRepository;

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
        Memo existingMemo = memoRepository.findByUserAndTargetTextAndContent(user, targetText, content);

        if (existingMemo != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 동일한 메모가 존재합니다.");
        }

        Memo memo = new Memo();
        memo.setPost(postRepository.findByPostId(id));
        memo.setUser(user);
        memo.setTargetText(targetText);
        memo.setContent(content);

        return memoRepository.save(memo);
    }

    public List<QuizResponse> createQuiz(Long id) {

        Optional<Post> optionalPost = postRepository.findById(id);
        Post post = optionalPost.orElseThrow(() -> new IllegalArgumentException("Post not found with ID: " + id));

//        String content = post.getContent();
        // 임시 content
        String content = "다층 신경망의 구조\n" +

                "문자 인식을 위한 신경망의 구조\n" +
                "신경망의 구조와 크기는 문제의 복잡도에 따라 달라진다.\n" +
                "\n" +
                "EX) 손으로 쓴 문자를 인식하려면 3~4개와 수백개의 뉴런을 포함하는 복잡한 다층 신경망이 필요\n" +
                "\n" +
                "문제가 복잡하면 윽닉층의 갯수를 늘린다. BUT!\n" +
                "너무 많이 늘릴수는 없다!!\n" +
                "\n" +
                "인쇄된 숫자 인식문제에 대해서는 은닉층이 1개인 3층 신경망으로도 충분히 정확하다.\n" +
                "\n" +
                "은닉층 5개의 뉴런이 있다.\n" +
                "은닉층과 출력층에 있는 뉴런은 시그모이드 활성화 함수 사용\n" +
                "역전파 알고리즘으로 학습\n" +
                "모멘텀 상수는 0.95\n" +
                "가로 5x 세로 9 = 45개의 입력층\n" +
                "\n" +
                "출력층에서 1~10 중에서 2의 값이 1에 가까운 값중 제일 크기 때문에 숫자 2로 인식한다.\n" +
                "\n" +
                "출력층은 0~9까지의 숫자를 구분해야 하기 때문에 10개이다.\n" +
                "\n" +
                "\n" +
                "다른 예시\n" +
                "소리인식\n" +
                "\n" +
                "소리의 스텍트럼을 분석해서 인식한다\n" +
                "\n" +
                "운전대 조종\n" +
                "\n" +
                "input은 전방의 이미지이다. 출력값은 좌회전 ~ 우회전이다.\n" +
                "\n" +
                "얼굴이 보고있는 방향 파악\n" +
                "\n" +
                "여러가지 사례를 넣으면서 가중치를 학습시킨다.\n" +
                "\n" +
                "가속 학습/홈필드 신경망/ 양방향 연상 메모리 /자기조직 신경망은 수업을 진행하지 않았다!\n" +
                "\n" +
                "요약\n" +
                "기계학습\n" +
                "기계 학습은 컴퓨터가 경험을 통해 학습하고 예를 통해 배우며 유추하여 학습하게 만드는 적응 메커니즘과 연관이있다.\n" +
                "\n" +
                "학습 능력은 시간이 지나면서 지능형 시스템의 성능을 개선\n" +
                "\n" +
                "가장 대표적인 접근법 중 하나는 인공 신경망\n" +
                "\n" +
                "워렌 맥클록과 월터 피츠는 인공 신경망의 기초가 되는 아이디어를 제공\n" +
                "\n" +
                "퍼셉트론\n" +
                "프랭크 로젠블랫이 제안한 간단한 신경망 형태\n" +
                "\n" +
                "맥클록 -피츠 뉴런 모델을 바탕으로 조정가능한 가중치와 하드리미터로 구성. 또한 학습은 실제 출력과 목표 출력 간 격차를 줄이도록 가중치를 조절하는 방식으로 진행 (초기 가중치는 임의로 할당)\n" +
                "\n" +
                "퍼셉트론은 선형으로 분리할 수 있는 함수만 학습가능\n" +
                "역전파 알고리즘으로 학습한 다층 퍼셉트론으로 한계를 극복 가능!\n" +
                "\n" +
                "다층 신경망\n" +
                "공급뉴런으로 이루어진 입력층, 계산 뉴런으로 이루어진 하나이상의 중간 or 은닉층, 계산 뉴런으로 이루어진 출력층이 있는 피드포워드 신경망이다.\n" +
                "\n" +
                "입력층 - 외부에서 신호를 받고 이 신호를 은닉층에 있는 모든 뉴런에 재분배.\n" +
                "은닉층 - 특성을 파악 뉴런의 가중치는 입력 패턴에 숨겨져있는 특성을 나타낸다.\n" +
                "출력층 - 전체 신경망의 출력 패턴을 정한다.\n" +
                "\n" +
                "훈련입력 패턴을 신경망의 입력층에 전달 - 출력층에서 출력 패턴이 생성될 때까지 각 층에 입력 패턴을 전파 - 출력패턴이 목표 패턴과 다르면 오차를 계산후 출력층에서 입력층으로 거꾸로 전파 이와중에 가중치를 수정!\n" +
                "\n" +
                "역전파 학습\n" +
                "널리 쓰이지만 계산 부담이 크고 학습이 느리다. 따라서 실제 응용할 때는 순수한 역전파 알고리즘을 거의 사용하지 않는다.\n" +
                "\n" +
                "다층 신경망은 시그모이드 활성화 할수가 쌍곡 탄젠트로 표현될때 좀더 빠르게 학습.\n" +
                "\n" +
                "끝!";


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
                "다음은 기사의 내용이야." + content;


        System.out.println(prompt);

        GeminiRequest request = new GeminiRequest(prompt);
//        GeminiResponse response = restTemplate.postForObject(requestUrl, request, GeminiResponse.class);
//
//        String message = response.getCandidates().get(0).getContent().getParts().get(0).getText().toString();
//        System.out.println("here message");
//        System.out.println(response);
        GeminiResponse response = restTemplate.postForObject(requestUrl, request, GeminiResponse.class);
        System.out.println("response = " + response);
        List<QuizResponse> quizSet = response.formatQuiz(id); // 리턴 형식 지정하는 함수
        System.out.println("quizSet = " + quizSet);
        return quizSet;


//        return (List<QuizResponse>) quizSet;
    }
}
