package com.ticle.server.opinion.repository;

import com.ticle.server.opinion.domain.Comment;
import com.ticle.server.opinion.domain.Opinion;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c " +
            "FROM Comment c JOIN FETCH Heart h ON c.commentId = h.comment.commentId " +
            "WHERE c.opinion = :opinion")
    List<Comment> findAllByOpinionWithFetch(Opinion opinion, Sort sort);
}
