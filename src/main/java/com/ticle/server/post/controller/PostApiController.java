package com.ticle.server.post.controller;

import com.ticle.server.global.dto.ResponseTemplate;
import com.ticle.server.memo.domain.Memo;
import com.ticle.server.memo.dto.MemoDto;
import com.ticle.server.memo.dto.MemoRequest;
import com.ticle.server.post.domain.Post;
import com.ticle.server.post.dto.PostResponse;
import com.ticle.server.post.service.PostService;
import com.ticle.server.scrapped.domain.Scrapped;
import com.ticle.server.scrapped.dto.ScrappedDto;
import com.ticle.server.user.domain.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Post", description = "아티클 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/post")  // 컨트롤러 레벨에서 /post 경로 설정
public class PostApiController {

    private final PostService postService;

    //카테고리로 아티클 조회
    @Operation(summary = "아티클 조회", description = "카테고리로 아티클 조회 | 공백 입력시 모든 카테고리의 아티클 나타남 ")
    @GetMapping
    public ResponseEntity<ResponseTemplate<Object>> findAllArticles(@RequestParam(required = false) String category) {

        System.out.println("come");
        List<PostResponse> posts = postService.findAllByCategory(category)
                .stream()
                .map(PostResponse::from)
                .collect(Collectors.toList());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(posts));
    }

    //특정 아티클 조회
    @Operation(summary = "특정 아티클 조회", description = "특정 아티클 조회")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseTemplate<Object>> findArticle(@PathVariable long id) {
        Post post = postService.findById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(post));
    }

    @Operation(summary = "아티클 스크랩", description = "새로운 아티클 스크랩, 스크랩 취소")
    @PostMapping("/{id}/scrap")
    public ResponseEntity<ResponseTemplate<Object>> scrappedArticle(@PathVariable long id, @AuthenticationPrincipal UserDetails userDetails) {

        Object scrapped = postService.scrappedById(id, userDetails);

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
    public ResponseEntity<ResponseTemplate<Object>> memoArticle(@PathVariable long id, @RequestBody MemoRequest memoRequest, @AuthenticationPrincipal UserDetails userDetails) {
        {
            Object memo = postService.writeMemo(id, userDetails, memoRequest.getTargetText(), memoRequest.getContent());

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ResponseTemplate.from(MemoDto.from((Memo) memo)));
        }
    }
}
