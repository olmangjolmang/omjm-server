package com.ticle.server.opinion.dto.response;

import com.ticle.server.opinion.domain.Opinion;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.List;

@Builder
public record OpinionResponse(
        Long opinionId,
        String question,
        Long viewCount,
        Long commentCount,
        @Size(max = 2, message = "최대 2개의 인기 댓글만 표시할 수 있습니다.")
        List<CommentShortResponse> comments
) {
    public static OpinionResponse from(Opinion opinion) {
        return OpinionResponse.builder()
                .opinionId(opinion.getOpinionId())
                .question(opinion.getQuestion())
                .viewCount(opinion.getViewCount())
                .commentCount(opinion.getCommentCount())
                .comments(opinion.getComments().stream()
                        .sorted((c1, c2) -> Long.compare(c2.getHeartCount(), c1.getHeartCount()))
                        .limit(2)
                        .map(CommentShortResponse::from)
                        .toList())
                .build();
    }
}