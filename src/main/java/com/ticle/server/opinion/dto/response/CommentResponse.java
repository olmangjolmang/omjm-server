package com.ticle.server.opinion.dto.response;

import com.ticle.server.opinion.domain.Comment;
import lombok.Builder;

@Builder
public record CommentResponse(
    Long commentId,
    String nickname,
    String content,
    Long heartCount,
    Boolean isHeart
) {
    public static CommentResponse of(Comment comment, Boolean isHeart) {
        return CommentResponse.builder()
                .commentId(comment.getCommentId())
                .nickname(comment.getUser().getNickName())
                .content(comment.getContent())
                .heartCount(comment.getHeartCount())
                .isHeart(isHeart)
                .build();
    }
}