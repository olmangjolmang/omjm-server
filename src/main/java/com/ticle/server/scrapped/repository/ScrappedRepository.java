package com.ticle.server.scrapped.repository;

import com.ticle.server.scrapped.domain.Scrapped;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScrappedRepository extends JpaRepository<Scrapped, Long> {
}
