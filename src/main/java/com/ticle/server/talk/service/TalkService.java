package com.ticle.server.talk.service;

import com.ticle.server.mypage.domain.User;
import com.ticle.server.mypage.repository.UserRepository;
import com.ticle.server.talk.domain.Comment;
import com.ticle.server.talk.domain.Talk;
import com.ticle.server.talk.dto.request.CommentUploadRequest;
import com.ticle.server.talk.exception.TalkNotFoundException;
import com.ticle.server.talk.repository.CommentRepository;
import com.ticle.server.talk.repository.TalkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ticle.server.talk.exception.errorcode.TalkErrorCode.TALK_NOT_FOUND;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TalkService {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final TalkRepository talkRepository;

    @Transactional
    public void uploadComment(CommentUploadRequest request, Long talkId, Long userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(RuntimeException::new);

        Talk talk = talkRepository.findByTalkId(talkId)
                .orElseThrow(() -> new TalkNotFoundException(TALK_NOT_FOUND));

        Comment comment = request.toComment(talk, user);

        commentRepository.save(comment);
    }
}
