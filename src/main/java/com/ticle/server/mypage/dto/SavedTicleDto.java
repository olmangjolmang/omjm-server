package com.ticle.server.mypage.dto;

import com.ticle.server.global.domain.S3Info;
import com.ticle.server.post.domain.Post;
import com.ticle.server.user.domain.type.Category;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class SavedTicleDto {
    private Long postId;
    private String title;
    private String content;
    private String author;
    private LocalDateTime createDate;
    private Category postCategory;
    private S3Info image;

    public static SavedTicleDto toDto(Post post) {
        return SavedTicleDto.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor())
                .createDate(post.getCreatedDate())
                .postCategory(post.getCategory())
                .image(post.getImage())
                .build();
    }



}
