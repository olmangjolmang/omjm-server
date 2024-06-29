package com.ticle.server.talk.repository;

import com.ticle.server.mypage.domain.User;
import com.ticle.server.talk.domain.Comment;
import com.ticle.server.talk.domain.Heart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HeartRepository extends JpaRepository<Heart, Long> {

    Optional<Heart> findHeartByUserAndComment(User user, Comment comment);

    Boolean existsByUserAndComment(User user, Comment comment);
}
