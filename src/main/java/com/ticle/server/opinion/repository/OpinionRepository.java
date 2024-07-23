package com.ticle.server.opinion.repository;

import com.ticle.server.opinion.domain.Opinion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OpinionRepository extends JpaRepository<Opinion, Long> {

    @Query("SELECT o " +
            "FROM Opinion o JOIN FETCH Comment c on o.opinionId = c.opinion.opinionId " +
            "WHERE o.opinionId = :opinionId")
    Optional<Opinion> findByOpinionIdWithFetch(@Param("opinionId") Long opinionId);

    @EntityGraph(attributePaths = {"comments", "comments.user"})
    Page<Opinion> findAll(Pageable pageable);




//    Optional<Opinion> findByOpinionId(Long opinionId);
}
