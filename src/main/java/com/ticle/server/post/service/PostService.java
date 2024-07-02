package com.ticle.server.post.service;

import com.ticle.server.mypage.domain.type.Category;
import com.ticle.server.post.domain.Post;
import com.ticle.server.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    // 모든 글 조회
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    // 카테고리에 맞는 글 찾기
    public List<Post> findAllByCategory(String category) {
        Category enumCategory;
        try {
            enumCategory = Category.valueOf(category.toUpperCase());  // 문자열을 Enum으로 변환
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid category: " + category);
        }
        return postRepository.findByCategory(enumCategory);
    }
}
