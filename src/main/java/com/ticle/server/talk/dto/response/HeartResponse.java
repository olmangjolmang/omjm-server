package com.ticle.server.talk.dto.response;

import com.ticle.server.talk.domain.Comment;
import com.ticle.server.talk.domain.Heart;
import com.ticle.server.user.domain.User;

public record HeartResponse(
        User user,
        Comment comment
) {
    public static Heart of(User user, Comment comment) {
        return Heart.builder()
                .user(user)
                .comment(comment)
                .build();
    }
}