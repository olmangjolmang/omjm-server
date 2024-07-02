package com.ticle.server.post.controller;

import com.ticle.server.post.dto.PostResponse;
import com.ticle.server.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class PostApiController {

    private final PostService postService;

    @GetMapping("/api/post")
    public ResponseEntity<List<PostResponse>> findAllArticles(@RequestParam(required = false) String category) {
        List<PostResponse> posts;

        if (category != null && !category.isEmpty()) {
            posts = postService.findAllByCategory(category)
                    .stream()
                    .map(PostResponse::new)
                    .collect(Collectors.toList());
        } else {
            posts = postService.findAll()
                    .stream()
                    .map(PostResponse::new)
                    .collect(Collectors.toList());
        }

        return ResponseEntity.ok().body(posts);
    }
}
