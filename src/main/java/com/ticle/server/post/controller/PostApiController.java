package com.ticle.server.post.controller;

import com.ticle.server.post.domain.Post;
import com.ticle.server.post.dto.PostResponse;
import com.ticle.server.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/post")  // 컨트롤러 레벨에서 /post 경로 설정
public class PostApiController {

    private final PostService postService;

    //카테고리로 아티클 조회
    @GetMapping
    public ResponseEntity<List<PostResponse>> findAllArticles(@RequestParam(required = false) String category) {
        List<PostResponse> posts;

        if (category != null && !category.isEmpty()) {
            //카테고리가 있을 경우
            posts = postService.findAllByCategory(category)
                    .stream()
                    .map(PostResponse::new)
                    .collect(Collectors.toList());
        } else {
            //카테고리가 없어 모든 아티클을 조회함
            posts = postService.findAll()
                    .stream()
                    .map(PostResponse::new)
                    .collect(Collectors.toList());
        }

        return ResponseEntity.ok().body(posts);
    }

    //특정 아티클 조회
    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> findArticle(@PathVariable long id) {
        Post post = postService.findById(id);

        return ResponseEntity.ok().body(new PostResponse(post));
    }

}
