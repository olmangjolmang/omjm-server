package com.ticle.server.mypage.dto;

import com.ticle.server.opinion.domain.Opinion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class MyQuestionDto {
    private Long questionId;
    private String question;
    private Long viewCount;
    private Long commentCount;

    public static MyQuestionDto toDto(Opinion opinion){
        return MyQuestionDto.builder()
                .questionId(opinion.getOpinionId())
                .question(opinion.getQuestion())
                .viewCount(opinion.getViewCount())
                .commentCount(opinion.getCommentCount())
                .build();
    }
}
