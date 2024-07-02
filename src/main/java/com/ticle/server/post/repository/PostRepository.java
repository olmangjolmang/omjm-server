package com.ticle.server.post.repository;

import com.ticle.server.post.domain.Post;
import com.ticle.server.talk.domain.Talk;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>{
    List<Post> findByUserId(Long userId);

}
