package com.ticle.server.talk.repository;

import com.ticle.server.talk.domain.Talk;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TalkRepository extends JpaRepository<Talk, Long> {

    @Query("SELECT t " +
            "FROM Talk t JOIN FETCH Comment c on t.talkId = c.talk.talkId " +
            "WHERE t.talkId = :talkId")
    Optional<Talk> findByTalkIdWithFetch(Long talkId);

    @EntityGraph(attributePaths = {"comments", "comments.user"})
    Page<Talk> findAll(Pageable pageable);
}
