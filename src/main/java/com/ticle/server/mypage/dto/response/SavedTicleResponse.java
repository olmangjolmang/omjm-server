package com.ticle.server.mypage.dto.response;

import com.ticle.server.global.domain.S3Info;
import com.ticle.server.post.domain.Post;
import com.ticle.server.user.domain.type.Category;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class SavedTicleResponse {
    private Long postId;
    private String title;
    private String content;
    private String author;
    private LocalDateTime createDate;
    private Category postCategory;
    private S3Info image;

    public static SavedTicleResponse toDto(Post post) {
        return SavedTicleResponse.builder()
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
