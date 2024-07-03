package com.ticle.server.talk.repository;

import com.ticle.server.talk.domain.Comment;
import com.ticle.server.talk.domain.Talk;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findByCommentId(Long commentId);

    @Query("SELECT comment " +
            "FROM Comment comment JOIN FETCH Heart heart ON comment.commentId = heart.comment.commentId " +
            "WHERE comment.talk = :talk")
    List<Comment> findAllByTalk(Talk talk, Sort sort);
}
