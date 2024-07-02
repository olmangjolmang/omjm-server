package com.ticle.server.mypage.dto;

import lombok.Data;

@Data
public class MyQuestionDto {
    private Long key;
    private String question;
    private Long view;
    private Long commentCount;
}
