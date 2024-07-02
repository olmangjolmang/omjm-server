package com.ticle.server.mypage.repository;

import com.ticle.server.scrapped.domain.Scrapped;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScrapRepository extends JpaRepository<Scrapped, Long> {
    List<Scrapped> findByUserId(Long userId);

}
