package com.ticle.server.talk.application;

import com.ticle.server.talk.domain.Talk;
import com.ticle.server.talk.dto.response.TalkResponse;
import com.ticle.server.talk.repository.CommentRepository;
import com.ticle.server.talk.repository.TalkRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class TalkService {

    private final TalkRepository talkRepository;
    private final CommentRepository commentRepository;

    public List<TalkResponse> getTalks(Long talkId){
        List<Talk> talks = talkRepository.findByTalkId(talkId);

        return talks.stream()
                .map(TalkResponse::toDto)
                .collect(toList());
    }
}
