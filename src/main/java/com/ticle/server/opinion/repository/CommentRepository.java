package com.ticle.server.opinion.repository;

import com.ticle.server.opinion.domain.Comment;
import com.ticle.server.opinion.domain.Opinion;
import com.ticle.server.user.domain.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @EntityGraph(attributePaths = {"hearts", "user"})
    List<Comment> findAllByOpinion(Opinion opinion, Sort sort);
    Comment findByUserAndOpinion(User user,Opinion opinion);
}
