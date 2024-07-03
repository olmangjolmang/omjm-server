package com.ticle.server.mypage.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MyNoteDto {
    private Long memoId;
    private String content;
    private LocalDateTime memoDate;
    private Long postId;
    private String postTitle;
}
