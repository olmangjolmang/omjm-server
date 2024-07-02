package com.ticle.server.mypage.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SavedTicleDto {
    private Long postId;
    private String title;
    private String content;
    private String author;
    private LocalDateTime createDate;
    private String postCategory;
    private String image;
}
