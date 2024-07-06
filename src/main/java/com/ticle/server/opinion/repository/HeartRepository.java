package com.ticle.server.opinion.repository;

import com.ticle.server.opinion.domain.Comment;
import com.ticle.server.opinion.domain.Heart;
import com.ticle.server.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HeartRepository extends JpaRepository<Heart, Long> {

    Optional<Heart> findHeartByUserAndComment(User user, Comment comment);
}
