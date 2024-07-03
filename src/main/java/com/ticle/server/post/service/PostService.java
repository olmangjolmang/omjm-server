package com.ticle.server.post.service;

import com.ticle.server.post.domain.Post;
import com.ticle.server.post.repository.PostRepository;
import com.ticle.server.scrapped.domain.Scrapped;
import com.ticle.server.scrapped.repository.ScrappedRepository;
import com.ticle.server.user.domain.User;
import com.ticle.server.user.domain.type.Category;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
        // 게시물 조회
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + id));

        Scrapped scrapped = new Scrapped();
        scrapped.setPost(post);

//        // 아래 주석은 로그인 정상 작동 시 주석 해제할 것 (현재는 user_id 제외하고 들어감)
//
//        // 현재 로그인한 유저 정보
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Object principal = authentication.getPrincipal();
//
//        if (principal instanceof User) { //스트링이면
//            scrapped.setUser((User) principal); //유저 객체화
//        } else {
//            // 인증된 사용자 정보가 User 객체가 아니면 에러발생
//            throw new IllegalStateException("Authenticated principal is not of type User: " + principal);
//        }

        return scrappedRepository.save(scrapped);
    }
}
