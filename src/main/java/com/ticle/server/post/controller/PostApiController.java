package com.ticle.server.post.controller;

import com.ticle.server.global.dto.ResponseTemplate;
import com.ticle.server.memo.domain.Memo;
import com.ticle.server.memo.dto.MemoDto;
import com.ticle.server.memo.dto.MemoRequest;
import com.ticle.server.post.domain.Post;
import com.ticle.server.post.domain.type.PostSort;
import com.ticle.server.post.dto.PostIsSavedResponse;
import com.ticle.server.post.dto.PostResponse;
import com.ticle.server.post.dto.QuizResponse;
import com.ticle.server.post.service.PostService;
import com.ticle.server.scrapped.domain.Scrapped;
import com.ticle.server.scrapped.dto.ScrappedDto;
import com.ticle.server.user.domain.type.Category;
import com.ticle.server.user.jwt.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

@Tag(name = "Post", description = "아티클 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/post")  // 컨트롤러 레벨에서 /post 경로 설정
public class PostApiController {

    private final PostService postService;

    //아티클 조회 & 검색
    @Operation(summary = "아티클 조회 & 검색",
            description = "카테고리로 아티클 조회 | category 미입력시 모든 아티클, orderBy 미입력시 최신순, page 미입력시 첫 페이지. " +
                    "(keyword 입력시 제목에서 keyword 검색하여 필터링, 미입력시 필터링 없음)")
    @GetMapping
    public ResponseEntity<ResponseTemplate<Object>> getArticle(
            @RequestParam(required = false) Category category,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false, defaultValue = "LATEST") PostSort orderBy,
            @RequestParam(required = false, defaultValue = "1") Integer page) {

        Page<PostResponse> postPage = postService.getArticles(category, keyword, orderBy, page);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(postPage));
    }

    //특정 아티클 조회
    @Operation(summary = "특정 아티클 조회", description = "특정 아티클 조회")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseTemplate<Object>> getArticleDetail(@PathVariable long id) {
        Post post = postService.findArticleById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(PostResponse.from(post)));
    }

    //아티클의 스크랩 유무
    @Operation(summary = "특정 아티클의 스크랩 유무")
    @GetMapping("/is-scrapped/{id}")
    public ResponseEntity<ResponseTemplate<Object>> getArticleIsSaved(@PathVariable long id, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        boolean isScrapped = postService.ArticleIsScrapped(id, customUserDetails);

        PostIsSavedResponse response = new PostIsSavedResponse(id, isScrapped);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(response));
    }


    @Operation(summary = "함께 읽으면 좋을 아티클 추천", description = "함께 읽으면 좋을 아티클 추천")
    @GetMapping("/recommend/{id}")
    public ResponseEntity<ResponseTemplate<Object>> ReadRecommendPost(@PathVariable long id) {
        List<Post> posts = postService.ArticleReadRecommend(id);

        ResponseTemplate<Object> response = ResponseTemplate.from(posts);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @Operation(summary = "아티클 스크랩", description = "새로운 아티클 스크랩, 스크랩 취소")
    @PostMapping("/{id}/scrap")
    public ResponseEntity<ResponseTemplate<Object>> scrappedArticle(@PathVariable("id") Long id, @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        Object scrapped = postService.scrappedById(id, customUserDetails);

        if (scrapped instanceof ScrappedDto) { // 이미 스크랩 했던 경우(취소기능)
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ResponseTemplate.from(scrapped));

        } else { //새로 스크랩 하는 경우(스크랩 추가)
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ResponseTemplate.from(ScrappedDto.from((Scrapped) scrapped)));
        }

    }

    // post memo
    @Operation(summary = "메모", description = "메모 작성하기")
    @PostMapping("/memo/{id}")
    public ResponseEntity<ResponseTemplate<Object>> memoArticle(@PathVariable long id, @RequestBody MemoRequest memoRequest, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        {
            Object memo = postService.writeMemo(id, customUserDetails, memoRequest.getTargetText(), memoRequest.getContent());

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ResponseTemplate.from(MemoDto.from((Memo) memo)));
        }
    }


    @Operation(summary = "퀴즈", description = "퀴즈 생성")
    @GetMapping("/quiz/{id}")
    public ResponseEntity<ResponseTemplate<Object>> quiz(@PathVariable long id) {
        List<QuizResponse> quizResponses = postService.createQuiz(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(quizResponses));
    }
}

