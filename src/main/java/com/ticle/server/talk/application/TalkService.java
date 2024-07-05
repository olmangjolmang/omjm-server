package com.ticle.server.talk.application;

import com.ticle.server.talk.domain.Talk;
import com.ticle.server.talk.dto.response.TalkResponse;
import com.ticle.server.talk.repository.CommentRepository;
import com.ticle.server.talk.repository.TalkRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TalkService {

    private final TalkRepository talkRepository;
    private final CommentRepository commentRepository;

    public TalkResponse getTalkWithCommentCount(Long talkId){
        Talk talk = talkRepository.findById(talkId).orElseThrow(()-> new RuntimeException("Talk not found"));
        Long commentCount = commentRepository.countByTalkId(talkId);

        return TalkResponse.toDto(talk,commentCount);
    }
}
