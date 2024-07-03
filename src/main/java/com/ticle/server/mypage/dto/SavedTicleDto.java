package com.ticle.server.mypage.dto;

import com.ticle.server.global.domain.S3Info;
import com.ticle.server.user.domain.type.Category;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SavedTicleDto {
    private Long postId;
    private String title;
    private String content;
    private String author;
    private LocalDateTime createDate;
    private Category postCategory;
    private S3Info image;
}
