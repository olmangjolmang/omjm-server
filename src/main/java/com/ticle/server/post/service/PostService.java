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
//        String content = post.getContent(); // 크롤링 완료시에 해당 코드로 진행.
        // 임시 content
        String content = "1. 클라이언트와 서버\n" +
                "A. 개념\n" +
                "클라이언트(client) : 서비스를 사용하는 컴퓨터(service user)\n" +
                "서버(server) : 서비스를 제공하는 컴퓨터(service provider)\n" +
                "\n" +
                "AJAX(Asynchronous Javascript And XML)\n" +
                "비동기식 Javascript와 XML. Javascript와 XML을 이용한 비동기적 정보 교환 기법으로, 웹에서 요청 시 서버와 데이터를 주고 받을때 사용한다.\n" +
                "\n" +
                "API(Application Programming Interface)\n" +
                "소프트웨어 애플리케이션이 서로 통신하여 데이터, 특징 및 기능을 교환할 수 있도록 하는 일련의 규칙 또는 프로토콜. 요약하자면, 컴퓨터가 소프트웨어와 상호작용하는 인터페이스. 쉽게 말해 은행의 창구와 같다.\n" +
                "\n" +
                "API가 데이터를 전송할때 쓰는 포맷은 크게 두 가지로, XML과 JSON이 있다. 최근에는 JSON을 주로 사용하는 추세다.\n" +
                "\n" +
                "JSON(Java Script Object Notation)\n" +
                "자바스크립트 객체 표기법. 그저 데이터 전송하는 포맷이라고 생각하면 된다. Javascript 객체 문법과 매우 유사하지만, Javascript가 아니더라도 JSON을 읽고 쓸 수 있는 기능이 다수의 프로그래밍 환경에서 제공한다.\n" +
                "\n" +
                "JSON은 자료형 Dictionary와 아주 유사한 Key : Value로 이루어져 있다.\n" +
                "\n" +
                "Client -> Server 요청 이해하기\n" +
                "클라이언트가 요청 할 때에도, \"타입\"이라는 것이 존재!\n" +
                "\n" +
                "GET : 통상적으로 데이터 조회(Read)를 요청할 때 사용. API로부터 정보를 가져온다.\n" +
                "POST : 통상적으로 데이터 생성(Create), 변경(Update), 삭제(Delete)를 요청 할 때 사용.\n" +
                "\n" +
                "\uD83D\uDC49 GET방식 이해하기\n" +
                "\n" +
                "예시) 영화 페이지\n" +
                "\n" +
                "https://movie.daum.net/moviedb/main?movieId=68593\n" +
                "\n" +
                "위 주소는 ?을 기준으로 두 부분으로 쪼개진다.\n" +
                "? 기준으로 앞 부분이 서버 주소, 뒷 부분이 영화 번호를 의미한다.\n" +
                "\n" +
                "-> 서버 주소: https://movie.daum.net/moviedb/main\n" +
                "-> 영화 정보: movieId=68593\n" +
                "\n" +
                "? : 여기서부터 전달할 데이터가 작성된다는 의미.\n" +
                "& : 전달할 데이터가 더 있다는 뜻.\n" +
                "\n" +
                "예시) google.com/search?q=아이폰&sourceid=chrome&ie=UTF-8\n" +
                "\n" +
                "위 주소는 google.com의 search 창구에 다음 정보를 전달!\n" +
                "\n" +
                "      q=아이폰                   (검색어)\n" +
                "     sourceid=chrome          (브라우저 정보)\n" +
                "     ie=UTF-8                  (인코딩 정보)\n" +
                "✋ 이러한 형식은 프론트엔드 개발자와 백엔드 개발자가 미리 정해둔 약속과 같다.\n" +
                "\n" +
                "2. FETCH\n" +
                "fetch란?\n" +
                "JavaScript에서 서버로 네트워크 요청을 보내고 응답을 받을 수 있도록 해주는 메서드. 이름 그대로 무언갈 가져오는 것을 의미한다.\n" +
                "\n" +
                "fetch 기본 골격\n" +
                "fetch(\"URL 입력\").then(res => res.json()).then(data => { \n" +
                "\t...\n" +
                "})\n" +
                "미세먼지 OpenAPI를 이용하여 Fetch 실습\n" +
                "OpenAPI : 누구나 쓸 수 있도록 공개된(Open) API.\n" +
                "\n" +
                "url에 미세먼지 OpenAPI 주소 http://spartacodingclub.shop/sparta_api/seoulair를 입력한다.\n" +
                "\n" +
                ".ForEach()를 이용하여 각 값에 맞는 KEY 값을 대입해 원하는 정보를 가져온다!\n" +
                "\n" +
                "function q1() {\n" +
                "  let url = \"http://spartacodingclub.shop/sparta_api/seoulair\";\n" +
                "  fetch(url)\n" +
                "    .then((res) => res.json())\n" +
                "    .then((data) => {\n" +
                "    let rows = data[\"RealtimeCityAir\"][\"row\"];\n" +
                "\n" +
                "    $(\"#names-q1\").empty();\n" +
                "    rows.forEach((element) => {\n" +
                "      let gu_name = element[\"MSRSTE_NM\"];\n" +
                "      let gu_mise = element[\"IDEX_MVL\"];\n" +
                "\n" +
                "      let temp_html = `<li>${gu_name} : ${gu_mise}</li>`;\n" +
                "      $(\"#names-q1\").append(temp_html);\n" +
                "    });\n" +
                "  });\n" +
                "}\n" +
                ".empty()로 기존에 있던 기존 html을 초기화, .append()로 새로운 html을 추가한다.\n" +
                "\n" +
                "\n" +
                "[업데이트] 버튼 클릭 시 해당 정보 업데이트 확인!\n" +
                "\n" +
                "\n" +
                "Javascript 추가하기\n" +
                "40도가 넘는 지역은 빨간색으로 나타내보자!\n" +
                "\n" +
                ".bad {\n" +
                "\tcolor: red;\n" +
                "}\n" +
                "먼저 <style> 태그에 추가.\n" +
                "\n" +
                "let temp_html = gu_mise > 40 ? `<li class=\"bad\">${gu_name} : ${gu_mise}</li>` : `<li>${gu_name} : ${gu_mise}</li>`;\n" +
                "이후 temp_html에 html 형식을 그냥 대입하는 것이 아닌, (gu_mise > 40)을 붙여 각각의 조건에 따라 다른 값을 대입한다.\n";


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
            response = restTemplate.postForObject(requestUrl, request, GeminiResponse.class); //다시 문제 생성
        }

        List<QuizResponse> quizSet = response.formatQuiz(postTitle); // 리턴 형식 지정하는 함수
        System.out.println("quizSet = " + quizSet);
        return quizSet;

    }
}
