package com.ticle.server.mypage.repository;

import com.ticle.server.opinion.domain.Opinion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Opinion, Long> {
    List<Opinion> findByUserId(Long userId);
}
