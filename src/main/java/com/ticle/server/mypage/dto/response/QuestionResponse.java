package com.ticle.server.mypage.dto.response;

import com.ticle.server.opinion.domain.Opinion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class QuestionResponse {
    private Long questionId;
    private String question;
    private Long viewCount;
    private Long commentCount;

    public static QuestionResponse toDto(Opinion opinion){
        return QuestionResponse.builder()
                .questionId(opinion.getOpinionId())
                .question(opinion.getQuestion())
                .viewCount(opinion.getViewCount())
                .commentCount(opinion.getCommentCount())
                .build();
    }
}
