package com.ticle.server.scrapped.repository;

import com.ticle.server.scrapped.domain.Scrapped;
import com.ticle.server.user.domain.type.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface ScrappedRepository extends JpaRepository<Scrapped, Long> {
    Optional<Scrapped> findByUserIdAndPost_PostId(Long userId, Long postId);
    @Query("SELECT s FROM Scrapped s WHERE s.user.id = :userId AND s.status = 'SCRAPPED'")
    Page<Scrapped> findByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT s FROM Scrapped s WHERE s.user.id = :userId AND s.post.category = :category AND s.status = 'SCRAPPED'")
    Page<Scrapped> findByUserIdAndPostCategory(@Param("userId") Long userId, @Param("category") Category category, Pageable pageable);

}
