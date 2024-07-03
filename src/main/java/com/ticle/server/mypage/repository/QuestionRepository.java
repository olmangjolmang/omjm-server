package com.ticle.server.mypage.repository;

import com.ticle.server.talk.domain.Talk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Talk, Long> {
    List<Talk> findByUserId(Long userId);
}
