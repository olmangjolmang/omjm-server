package com.ticle.server.post.service;


import com.ticle.server.post.domain.Post;
import com.ticle.server.post.repository.PostRepository;
import com.ticle.server.scrapped.domain.Scrapped;
import com.ticle.server.scrapped.repository.ScrappedRepository;
import com.ticle.server.user.domain.type.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final ScrappedRepository scrappedRepository;

    // 카테고리에 맞는 글 찾기
    public List<Post> findAllByCategory(String category) {
        if (category == null || category.isEmpty()) {
            // 모든 글 조회
            return postRepository.findAll();
        } else {
            //카테고리에 맞는 글 조회
            return postRepository.findByCategory(Category.valueOf(category));
        }
    }

    //postId로 조회한 특정 post 정보 리턴
    public Post findById(long id) {
        return postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }


    public Scrapped scrappedById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found" + id));
        Scrapped scrapped = new Scrapped();
        scrapped.setPost(post);

        return scrappedRepository.save(scrapped);
    }
}
