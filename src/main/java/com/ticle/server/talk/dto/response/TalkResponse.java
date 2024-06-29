package com.ticle.server.talk.dto.response;

import com.ticle.server.talk.domain.Comment;
import lombok.Builder;

@Builder
public record TalkResponse(
    String nickname,
    String content,
    Long heartCount,
    Boolean isHeart
) {
    public static TalkResponse of(Comment comment, Boolean isHeart) {
        return TalkResponse.builder()
                .nickname(comment.getUser().getNickname())
                .content(comment.getContent())
                .heartCount(comment.getHeartCount())
                .isHeart(isHeart)
                .build();
    }
}