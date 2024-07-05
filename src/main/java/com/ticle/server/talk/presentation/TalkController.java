package com.ticle.server.talk.presentation;

import com.ticle.server.talk.application.TalkService;
import com.ticle.server.talk.dto.response.TalkResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/talks")
public class TalkController {

    private final TalkService talkService;

    @Autowired
    public TalkController(TalkService talkService) {
        this.talkService = talkService;
    }

    @GetMapping("/{talkId}")
    public List<TalkResponse> getMyQuestions(@PathVariable("talkId") Long talkId) {
        return talkService.getTalks(talkId);
    }


}

