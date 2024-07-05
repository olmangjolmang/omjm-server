package com.ticle.server.scrapped.repository;

import com.ticle.server.scrapped.domain.Scrapped;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ScrappedRepository extends JpaRepository<Scrapped, Long> {
    Optional<Scrapped> findByUserIdAndPost_PostId(Long userId, Long postId);

}
