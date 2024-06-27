package com.ticle.server.talk.service;

import com.ticle.server.mypage.domain.User;
import com.ticle.server.talk.dto.request.CommentUploadRequest;
import com.ticle.server.talk.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TalkService {

    private final CommentRepository commentRepository;

    @Transactional
    public void uploadComment(CommentUploadRequest request, Long userId) {
    }
}
