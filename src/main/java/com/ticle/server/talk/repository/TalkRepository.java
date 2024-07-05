package com.ticle.server.talk.repository;

import com.ticle.server.talk.domain.Talk;
import com.ticle.server.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TalkRepository extends JpaRepository<Talk,Long> {
    Optional<Talk> findByTalkId(Long talkId);

}
