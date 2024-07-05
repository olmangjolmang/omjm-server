package com.ticle.server.talk.repository;

import com.ticle.server.talk.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

//    @Query("SELECT COUNT(c) FROM Comment c WHERE c.talk.talkId = :talkId")
//    Long countByTalkId(@Param("talkId") Long talkId);
}
