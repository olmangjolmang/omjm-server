package com.ticle.server.mypage.repository;

import com.ticle.server.scrapped.domain.Scrapped;
import com.ticle.server.user.domain.type.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScrapRepository extends JpaRepository<Scrapped, Long> {
    Page<Scrapped> findByUserId(Long userId,Pageable pageable);
    Page<Scrapped> findByUserIdAndPostCategory(@Param("userid") Long userId, @Param("category") Category category,Pageable pageable);

//    Page<Scrapped> findAll(Pageable pageable);
}
