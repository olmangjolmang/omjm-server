package com.ticle.server.post.repository;

import com.ticle.server.home.dto.response.PostSetsResponse;
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
    Page<Post> findByCategory(Category category, Pageable pageable);

    @Query("SELECT new com.ticle.server.post.dto.PostIdTitleDto(p.postId, p.title) FROM Post p")
    List<PostIdTitleDto> findAllPostSummaries();

    // 카테고리 검색
    @Query("SELECT p " +
            "FROM Post p " +
            "WHERE p.category = :category " +
            "ORDER BY p.createdDate DESC LIMIT 1")
    Optional<Post> findTopPostByCategory(@Param("category") List<Category> category);

    @Query("SELECT new com.ticle.server.home.dto.response.PostSetsResponse(p.title, p.image.imageUrl, p.category, p.author, p.createdDate) " +
            "FROM Post p " +
            "WHERE p.postId IN (:postIds)")
    List<PostSetsResponse> findSelectedPostInfoByIds(@Param("postIds") Set<Long> postIds);

    @Query("SELECT new com.ticle.server.home.dto.response.PostSetsResponse(p.title, p.image.imageUrl, p.category, p.author, p.createdDate) " +
            "FROM Post p " +
            "ORDER BY p.scrapCount DESC LIMIT 3")
    List<PostSetsResponse> findTop3ByOrderByScrapCountDesc();

    @Query("SELECT p FROM Post p WHERE " +
            "(:keyword IS NULL OR p.title LIKE %:keyword%) AND " +
            "(:category IS NULL OR p.category = :category)")
    Page<Post> findByKeywordAndCategory(
            @Param("category") Category category,
            @Param("keyword") String keyword,
            Pageable pageable);
}
