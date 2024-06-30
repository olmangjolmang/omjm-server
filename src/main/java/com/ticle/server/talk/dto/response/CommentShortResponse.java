package com.ticle.server.talk.dto.response;

import com.ticle.server.talk.domain.Comment;
import lombok.Builder;

@Builder
public record CommentShortResponse(
        String nickname,
        String content
) {
    public static CommentShortResponse from(Comment comment) {
        return CommentShortResponse.builder()
                .nickname(comment.getUser().getNickname())
                .content(comment.getContent())
                .build();
    }
}