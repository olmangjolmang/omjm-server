package com.ticle.server.talk.dto.response;

import com.ticle.server.talk.domain.Comment;
import lombok.Builder;

@Builder
public record CommentResponse(
    String nickname,
    String content,
    Long heartCount,
    Boolean isHeart
) {
    public static CommentResponse of(Comment comment, Boolean isHeart) {
        return CommentResponse.builder()
                .nickname(comment.getUser().getNickName())
                .content(comment.getContent())
                .heartCount(comment.getHeartCount())
                .isHeart(isHeart)
                .build();
    }
}