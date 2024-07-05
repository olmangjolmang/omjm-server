package com.ticle.server.talk.application;

import com.ticle.server.talk.domain.Comment;
import com.ticle.server.talk.domain.Talk;
import com.ticle.server.talk.repository.CommentRepository;
import com.ticle.server.talk.repository.TalkRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final TalkRepository talkRepository;

    @Transactional
    public Comment addComment(Long talkId, String content, Long heart) {
        Talk talk = talkRepository.findById(talkId)
                .orElseThrow(() -> new RuntimeException("Talk not found"));
        Comment comment = Comment.builder()
                .content(content)
                .heart(heart)
                .talk(talk)
                .build();
        Comment savedComment = commentRepository.save(comment);
        talk.incrementCommentCount();
        talkRepository.save(talk);  // Talk 엔티티의 상태를 업데이트
        return savedComment;
    }

    @Transactional
    public void removeComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        Talk talk = comment.getTalk();
        commentRepository.delete(comment);
        talk.decrementCommentCount();
        talkRepository.save(talk);  // Talk 엔티티의 상태를 업데이트
    }
}
