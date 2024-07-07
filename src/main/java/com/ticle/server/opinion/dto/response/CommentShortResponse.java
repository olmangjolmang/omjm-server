package com.ticle.server.opinion.dto.response;

import com.ticle.server.opinion.domain.Comment;
import lombok.Builder;

@Builder
public record CommentShortResponse(
        String nickname,
        String content
) {
    public static CommentShortResponse from(Comment comment) {
        return CommentShortResponse.builder()
                .nickname(comment.getUser().getNickName())
                .content(comment.getContent())
                .build();
    }
}