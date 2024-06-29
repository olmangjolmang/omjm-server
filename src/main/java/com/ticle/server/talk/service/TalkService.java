package com.ticle.server.talk.service;

import com.ticle.server.mypage.domain.User;
import com.ticle.server.mypage.repository.UserRepository;
import com.ticle.server.talk.domain.Comment;
import com.ticle.server.talk.domain.Heart;
import com.ticle.server.talk.domain.Talk;
import com.ticle.server.talk.dto.request.CommentUploadRequest;
import com.ticle.server.talk.dto.response.CommentResponse;
import com.ticle.server.talk.exception.CommentNotFoundException;
import com.ticle.server.talk.exception.TalkNotFoundException;
import com.ticle.server.talk.repository.CommentRepository;
import com.ticle.server.talk.repository.HeartRepository;
import com.ticle.server.talk.repository.TalkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.ticle.server.talk.exception.errorcode.TalkErrorCode.COMMENT_NOT_FOUND;
import static com.ticle.server.talk.exception.errorcode.TalkErrorCode.TALK_NOT_FOUND;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TalkService {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final TalkRepository talkRepository;
    private final HeartRepository heartRepository;

    @Transactional
    public void uploadComment(CommentUploadRequest request, Long talkId, Long userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(RuntimeException::new);

        Talk talk = talkRepository.findByTalkId(talkId)
                .orElseThrow(() -> new TalkNotFoundException(TALK_NOT_FOUND));

        Comment comment = request.toComment(talk, user);

        commentRepository.save(comment);
    }

    @Transactional
    public void heartComment(Long commentId, Long userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(RuntimeException::new);

        Comment comment = commentRepository.findByCommentId(commentId)
                .orElseThrow(() -> new CommentNotFoundException(COMMENT_NOT_FOUND));

        Optional<Heart> existHeart = heartRepository.findHeartByUserAndComment(user, comment);

        // 내가 좋아요를 누른 경우
        if (existHeart.isPresent()) {
            heartRepository.delete(existHeart.get());
            comment.heartChange(comment.getHeartCount() - 1);
        } else {
            Heart heart = Heart.builder()
                    .user(user)
                    .comment(comment)
                    .build();

            heartRepository.save(heart);
            comment.heartChange(comment.getHeartCount() + 1);
        }
    }

    public List<CommentResponse> getComments(Long talkId, Long userId, String orderBy) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(RuntimeException::new);

        Talk talk = talkRepository.findByTalkId(talkId)
                .orElseThrow(() -> new TalkNotFoundException(TALK_NOT_FOUND));

        Sort sort;
        if ("heart".equalsIgnoreCase(orderBy)) {
            sort = Sort.by(Sort.Order.desc("heartCount"), Sort.Order.desc("createdDate"));
        } else {
            sort = Sort.by(Sort.Order.desc("createdDate"));
        }

        List<Comment> comments = commentRepository.findAllByTalk(talk, sort);

        return comments.stream()
                .map(comment -> {
                    boolean isHeart = heartRepository.existsByUserAndComment(user, comment);
                    return CommentResponse.of(comment, isHeart);})
                .toList();
    }
}