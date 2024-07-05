package com.ticle.server.talk.dto.response;

import com.ticle.server.talk.domain.Talk;

public record TalkResponse(
        Long talkId,
        Long userId,
        Long commentCount,
        Long view,
        String question
) {
    public static TalkResponse toDto(Talk talk) {
        return new TalkResponse(
                talk.getTalkId(),
                talk.getUser().getId(),
                talk.getCommentCount(),
                talk.getView(),
                talk.getQuestion()
        );
    }
}
