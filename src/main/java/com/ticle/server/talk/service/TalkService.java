package com.ticle.server.talk.service;

import com.ticle.server.talk.domain.Comment;
import com.ticle.server.talk.domain.Talk;
import com.ticle.server.talk.domain.type.Order;
import com.ticle.server.talk.dto.request.CommentUploadRequest;
import com.ticle.server.talk.dto.response.CommentResponse;
import com.ticle.server.talk.dto.response.HeartResponse;
import com.ticle.server.talk.dto.response.TalkResponse;
import com.ticle.server.talk.exception.CommentNotFoundException;
import com.ticle.server.talk.exception.TalkNotFoundException;
import com.ticle.server.talk.repository.CommentRepository;
import com.ticle.server.talk.repository.HeartRepository;
import com.ticle.server.talk.repository.TalkRepository;
import com.ticle.server.user.domain.User;
import com.ticle.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        User user = userRepository.findById(userId)
                .orElseThrow(RuntimeException::new);
        Talk talk = talkRepository.findById(talkId)
                .orElseThrow(() -> new TalkNotFoundException(TALK_NOT_FOUND));

        Comment comment = request.toComment(talk, user);

        commentRepository.save(comment);
    }

    @Transactional
    public void heartComment(Long commentId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(RuntimeException::new);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(COMMENT_NOT_FOUND));

        heartRepository.findHeartByUserAndComment(user, comment)
                .ifPresentOrElse(
                        existHeart -> {
                            heartRepository.delete(existHeart);
                            comment.subHeartCount();
                        },
                        () -> {
                            heartRepository.save(HeartResponse.of(user, comment));
                            comment.addHeartCount();
                        }
                );
    }

    @Transactional
    public List<CommentResponse> getComments(Long talkId, Long userId, Order orderBy) {
        User user = userRepository.findById(userId)
                .orElseThrow(RuntimeException::new);
        Talk talk = talkRepository.findByTalkId(talkId)
                .orElseThrow(() -> new TalkNotFoundException(TALK_NOT_FOUND));

        // 질문에 대한 댓글들 보기 위해 클릭했을 시 질문 조회수 +1
        talk.addViewCount();

        Sort sort = getOrder(orderBy);

        List<Comment> comments = commentRepository.findAllByTalk(talk, sort);

        return comments.stream()
                .map(comment -> {
                    boolean isHeart = heartRepository.existsByUserAndComment(user, comment);
                    return CommentResponse.of(comment, isHeart);
                })
                .toList();
    }

    private static Sort getOrder(Order orderBy) {
        return switch (orderBy) {
            case TIME -> Sort.by(Sort.Order.desc("createdDate"));
            case HEART -> Sort.by(Sort.Order.desc("heartCount"));
        };
    }

    public List<TalkResponse> getTalks() {
        Sort sort = Sort.by(Sort.Order.desc("createdDate"));

        List<Talk> talks = talkRepository.findAll(sort);

        return talks.stream()
                .map(TalkResponse::from)
                .toList();
    }
}