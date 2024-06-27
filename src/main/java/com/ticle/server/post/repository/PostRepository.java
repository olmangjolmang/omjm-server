package com.ticle.server.post.repository;

import com.ticle.server.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>{

}
