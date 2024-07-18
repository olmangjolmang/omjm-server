package com.ticle.server.post.controller;

import com.ticle.server.global.dto.ResponseTemplate;
import com.ticle.server.memo.domain.Memo;
import com.ticle.server.memo.dto.MemoDto;
import com.ticle.server.memo.dto.MemoRequest;
import com.ticle.server.post.domain.Post;
import com.ticle.server.post.domain.type.PostSort;
import com.ticle.server.post.dto.PostResponse;
import com.ticle.server.post.dto.QuizResponse;
import com.ticle.server.post.service.PostService;
import com.ticle.server.scrapped.domain.Scrapped;
import com.ticle.server.scrapped.dto.ScrappedDto;
import com.ticle.server.user.domain.type.Category;
import com.ticle.server.user.service.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.data.domain.Page;

@Tag(name = "Post", description = "아티클 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/post")  // 컨트롤러 레벨에서 /post 경로 설정
public class PostApiController {

    private final PostService postService;

    //카테고리로 아티클 조회
    @Operation(summary = "아티클 조회", description = "카테고리로 아티클 조회 | post?category={category}&sortName={sortName}&page={page} 과 같은 형식으로 작동합니다. 카테고리 미입력시 모든 아티클, sortName 미입력시 최신순, page 미입력시 1번 페이지입니다. ")
    @GetMapping
    public ResponseEntity<ResponseTemplate<Object>>
    findAllArticles(@RequestParam(required = false, defaultValue = "") Category category,
                    @RequestParam(required = false, defaultValue = "최신순") PostSort sortName,
                    @RequestParam(required = false, defaultValue = "1") Integer page) {

        Page<PostResponse> postPage = postService.findAllByCategory(category, sortName, page);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(postPage));
    }

    //특정 아티클 조회
    @Operation(summary = "특정 아티클 조회", description = "특정 아티클 조회")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseTemplate<Object>> findArticle(@PathVariable long id) {
        Post post = postService.findById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(PostResponse.from((Post) post)));

    }

    @Operation(summary = "아티클 스크랩", description = "새로운 아티클 스크랩, 스크랩 취소")
    @PostMapping("/{id}/scrap")
    public ResponseEntity<ResponseTemplate<Object>> scrappedArticle(@PathVariable long id, @AuthenticationPrincipal CustomUserDetails customUserDetails) {

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

