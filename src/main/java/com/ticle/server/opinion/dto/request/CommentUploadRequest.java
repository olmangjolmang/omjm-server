package com.ticle.server.opinion.dto.request;

import com.ticle.server.opinion.domain.Comment;
import com.ticle.server.opinion.domain.Opinion;
import com.ticle.server.user.domain.User;
import jakarta.validation.constraints.NotNull;

public record CommentUploadRequest(
    @NotNull(message = "내용을 입력해주세요.")
    String content
) {
    public Comment toComment(Opinion opinion, User user) {
        return Comment
                .builder()
                .opinion(opinion)
                .content(content)
                .heartCount(0L)
                .user(user)
                .build();
    }
}
