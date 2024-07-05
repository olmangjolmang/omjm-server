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

    @Query("SELECT talk " +
            "FROM Talk talk JOIN FETCH Comment c on talk.talkId = c.talk.talkId " +
            "WHERE talk.talkId = :talkId")
    Optional<Talk> findByTalkId(Long talkId);

    @EntityGraph(attributePaths = {"comments", "comments.user"})
    Page<Talk> findAll(Pageable pageable);
}
