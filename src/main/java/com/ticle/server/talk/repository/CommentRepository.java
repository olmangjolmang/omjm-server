package com.ticle.server.talk.repository;

import com.ticle.server.talk.domain.Comment;
import com.ticle.server.talk.domain.Talk;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c " +
            "FROM Comment c JOIN FETCH Heart h ON c.commentId = h.comment.commentId " +
            "WHERE c.talk = :talk")
    List<Comment> findAllByTalkWithFetch(Talk talk, Sort sort);
}
