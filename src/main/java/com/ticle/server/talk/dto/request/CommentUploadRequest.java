package com.ticle.server.talk.dto.request;

import com.ticle.server.mypage.domain.User;
import com.ticle.server.talk.domain.Comment;
import jakarta.validation.constraints.NotNull;

public record CommentUploadRequest(
    @NotNull(message = "내용을 입력해주세요.")
    String content
) {
    public Comment toComment(User user) {
        return Comment
                .builder()
                .content(content)
                .heart(0L)
                .user(user)
                .build();
    }
}
