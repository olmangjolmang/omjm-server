package com.ticle.server.talk.dto.response;

import com.ticle.server.talk.domain.Talk;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.List;

@Builder
public record TalkResponse(
        String question,
        Long view,
        Long commentCount,
        @Size(max = 2, message = "최대 2개의 인기 댓글만 표시할 수 있습니다.")
        List<CommentShortResponse> comments
) {
    public static TalkResponse from(Talk talk) {
        return TalkResponse.builder()
                .question(talk.getQuestion())
                .view(talk.getView())
                .commentCount(talk.getCommentCount())
                .comments(talk.getComments().stream()
                        .sorted((c1, c2) -> Long.compare(c2.getHeartCount(), c1.getHeartCount()))
                        .limit(2)
                        .map(CommentShortResponse::from)
                        .toList())
                .build();
    }
}