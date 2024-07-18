package com.ticle.server.post.repository;

import com.ticle.server.post.domain.Post;
import com.ticle.server.post.dto.PostIdTitleDto;
import com.ticle.server.user.domain.type.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;


public interface PostRepository extends JpaRepository<Post, Long> {
    Post findByPostId(Long postId);

    Page<Post> findByCategory(Category category, Pageable pageable);

    @Query("SELECT new com.ticle.server.post.dto.PostIdTitleDto(p.postId, p.title) FROM Post p")
    List<PostIdTitleDto> findAllPostSummaries();

    @Query("SELECT p " +
            "FROM Post p " +
            "WHERE p.category = :category " +
            "ORDER BY p.createdDate DESC LIMIT 1")
    Optional<Post> findTopPostByCategory(@Param("category") Category category);

    List<Post> findAllByPostIdIn(Set<Long> topPostIds);

    List<Post> findTop3ByOrderByScrapCountDesc();
}
