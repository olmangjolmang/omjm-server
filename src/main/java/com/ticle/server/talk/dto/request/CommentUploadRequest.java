package com.ticle.server.talk.dto.request;

import com.ticle.server.talk.domain.Comment;
import com.ticle.server.talk.domain.Talk;
import com.ticle.server.user.domain.User;
import jakarta.validation.constraints.NotNull;

public record CommentUploadRequest(
    @NotNull(message = "내용을 입력해주세요.")
    String content
) {
    public Comment toComment(Talk talk, User user) {
        return Comment
                .builder()
                .talk(talk)
                .content(content)
                .heartCount(0L)
                .user(user)
                .build();
    }
}
