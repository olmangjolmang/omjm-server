package com.ticle.server.talk.dto.response;

import com.ticle.server.talk.domain.Talk;

public record TalkResponse(
        Long talkId,
        Long userId,
        Long commentCount,
        Long view,
        String question
) {
    public static TalkResponse toDto(Talk talk, Long commentCount) {
        return new TalkResponse(
                talk.getTalkId(),
                talk.getUser().getId(),
                commentCount,
                talk.getView(),
                talk.getQuestion()
        );
    }
}
