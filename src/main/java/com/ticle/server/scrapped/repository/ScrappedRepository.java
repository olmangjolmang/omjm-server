package com.ticle.server.scrapped.repository;

import com.ticle.server.scrapped.domain.Scrapped;
import com.ticle.server.user.domain.type.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface ScrappedRepository extends JpaRepository<Scrapped, Long> {
    Optional<Scrapped> findByUserIdAndPost_PostId(Long userId, Long postId);

    Page<Scrapped> findByUserId(Long userId, Pageable pageable);
    Page<Scrapped> findByUserIdAndPostCategory(@Param("userid") Long userId, @Param("category") Category category, Pageable pageable);

}
