package com.ticle.server.post.service;

import com.ticle.server.user.domain.type.Category;
import com.ticle.server.post.domain.Post;
import com.ticle.server.post.repository.PostRepository;
import com.ticle.server.scrapped.domain.Scrapped;
import com.ticle.server.scrapped.repository.ScrappedRepository;
import com.ticle.server.user.domain.User;
import com.ticle.server.user.domain.type.Category;
import com.ticle.server.user.service.UserService;
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
    private final UserService userService;

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
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 post 찾을 수 없음 id: " + id));

        Scrapped scrapped = new Scrapped();
        scrapped.setPost(post);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//            System.out.println("here");
//            System.out.println(userDetails);
            // getUsername에는 email이 들어있음.
            // email로 유저 찾고 id 찾도록 함.
            User user = userService.getLoginUserByEmail(userDetails.getUsername());
            Long userId = user.getId();

//            System.out.println("User ID: " + userId);
            scrapped.setUser(user);

        } else {
            throw new IllegalStateException("principal 없음");
        }
        return scrappedRepository.save(scrapped);
    }
}
