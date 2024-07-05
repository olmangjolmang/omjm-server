package com.ticle.server.post.controller;

import com.ticle.server.global.dto.ResponseTemplate;
import com.ticle.server.post.domain.Post;
import com.ticle.server.post.dto.PostResponse;
import com.ticle.server.post.service.PostService;
import com.ticle.server.scrapped.domain.Scrapped;
import com.ticle.server.scrapped.dto.ScrappedDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @Operation(summary = "아티클 조회", description = "카테고리로 아티클 조회 \n 공백 입력시 모든 아티클")
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
    @Operation(summary = "아티클 조회", description = "특정 아티클 조회")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseTemplate<Object>> findArticle(@PathVariable long id) {
        Post post = postService.findById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(post));
    }


    @PostMapping("/{id}/scrap")
    public ResponseEntity<ResponseTemplate<Object>> scrappedArticle(@PathVariable long id) {

        Object scrapped = postService.scrappedById(id);

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
}
