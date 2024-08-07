package com.ticle.server.opinion.service;

import com.ticle.server.global.dto.PageInfo;
import com.ticle.server.opinion.domain.Comment;
import com.ticle.server.opinion.domain.Opinion;
import com.ticle.server.opinion.domain.type.Order;
import com.ticle.server.opinion.dto.request.CommentUploadRequest;
import com.ticle.server.opinion.dto.response.*;
import com.ticle.server.opinion.exception.CommentNotFoundException;
import com.ticle.server.opinion.exception.OpinionNotFoundException;
import com.ticle.server.opinion.repository.CommentRepository;
import com.ticle.server.opinion.repository.HeartRepository;
import com.ticle.server.opinion.repository.OpinionRepository;
import com.ticle.server.user.domain.User;
import com.ticle.server.user.exception.UserNotFoundException;
import com.ticle.server.user.exception.UserNotLoginException;
import com.ticle.server.user.repository.UserRepository;
import com.ticle.server.user.jwt.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.ticle.server.opinion.domain.type.Order.TIME;
import static com.ticle.server.opinion.domain.type.Order.getOrder;
import static com.ticle.server.opinion.exception.errorcode.OpinionErrorCode.COMMENT_NOT_FOUND;
import static com.ticle.server.opinion.exception.errorcode.OpinionErrorCode.OPINION_NOT_FOUND;
import static com.ticle.server.user.exception.errorcode.UserErrorCode.USER_NOT_FOUND;
import static com.ticle.server.user.exception.errorcode.UserErrorCode.USER_NOT_LOGIN;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OpinionService {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final OpinionRepository opinionRepository;
    private final HeartRepository heartRepository;

    private static final int PAGE_SIZE = 5;

    @Transactional
    public void uploadComment(CommentUploadRequest request, Long talkId, CustomUserDetails userDetails) {
        if (ObjectUtils.isEmpty(userDetails)) {
            throw new UserNotLoginException(USER_NOT_LOGIN);
        }

        User user = userRepository.findById(userDetails.getUserId())
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
        Opinion opinion = opinionRepository.findById(talkId)
                .orElseThrow(() -> new OpinionNotFoundException(OPINION_NOT_FOUND));

        Comment comment = request.toComment(opinion, user);
        opinion.addCommentCount();

        commentRepository.save(comment);
    }

    @Transactional
    public void heartComment(Long commentId, CustomUserDetails userDetails) {
        if (ObjectUtils.isEmpty(userDetails)) {
            throw new UserNotLoginException(USER_NOT_LOGIN);
        }

        User user = userRepository.findById(userDetails.getUserId())
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
    public CommentResponseList getCommentsByOpinion(Long opinionId, CustomUserDetails userDetails, Order orderBy) {
        Opinion opinion = opinionRepository.findByOpinionIdWithFetch(opinionId)
                .orElse(opinionRepository.findById(opinionId)
                        .orElseThrow(() -> new OpinionNotFoundException(OPINION_NOT_FOUND)));

        // 질문에 대한 댓글들 보기 위해 클릭했을 시 질문 조회수 +1
        opinion.addViewCount();

        // 로그인 하지 않은 유저라면 "", 로그인 한 유저라면 닉네임을 반환
        String userNickname = getUserNickname(userDetails);

        Sort sort = getOrder(orderBy);
        List<Comment> comments = commentRepository.findAllByOpinion(opinion, sort);

        List<CommentResponse> responses = comments.stream()
                .map(comment -> {
                    boolean isHeart = false;

                    if (ObjectUtils.isNotEmpty(userDetails)) {
                        User user = userRepository.findById(userDetails.getUserId())
                                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));

                        isHeart = comment.getHearts().stream()
                                .anyMatch(heart -> heart.getUser().equals(user));
                    }

                    return CommentResponse.of(comment, isHeart);
                })
                .toList();

        return CommentResponseList.of(opinion.getQuestion(), ObjectUtils.isNotEmpty(userDetails) ? userNickname : "", responses);
    }

    private String getUserNickname(CustomUserDetails userDetails) {
        String userNickname = "";

        if (ObjectUtils.isNotEmpty(userDetails)) {
            User user = userRepository.findById(userDetails.getUserId())
                    .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));

            userNickname = user.getNickName();
        }
        return userNickname;
    }

    public OpinionResponseList getOpinionsByPage(int page) {
        Pageable pageable = PageRequest.of(page - 1, PAGE_SIZE, getOrder(TIME));

        Page<Opinion> opinionPage = opinionRepository.findAll(pageable);
        PageInfo pageInfo = PageInfo.from(opinionPage);

        List<OpinionResponse> opinionResponseList = opinionPage.stream()
                .map(OpinionResponse::from)
                .toList();

        return OpinionResponseList.of(pageInfo, opinionResponseList);
    }
}