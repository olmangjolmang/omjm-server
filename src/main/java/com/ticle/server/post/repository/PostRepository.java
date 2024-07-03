package com.ticle.server.post.repository;

import com.ticle.server.post.domain.Post;
import com.ticle.server.user.domain.type.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByCategory(Category category);

}
