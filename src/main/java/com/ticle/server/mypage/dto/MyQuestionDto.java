package com.ticle.server.mypage.dto;

import com.ticle.server.talk.domain.Talk;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class MyQuestionDto {
    private Long questionId;
    private String question;
    private Long view;
    private Long commentCount;

    public static MyQuestionDto toDto(Talk talk){
        return MyQuestionDto.builder()
                .questionId(talk.getTalkId())
                .question(talk.getQuestion())
                .view(talk.getViewCount())
                .commentCount(talk.getCommentCount())
                .build();
    }
}
