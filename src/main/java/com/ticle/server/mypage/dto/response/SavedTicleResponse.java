package com.ticle.server.mypage.dto.response;

import com.ticle.server.global.domain.S3Info;
import com.ticle.server.post.domain.Post;
import com.ticle.server.scrapped.domain.Scrapped;
import com.ticle.server.user.domain.type.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Getter
@AllArgsConstructor
@Builder
public class SavedTicleResponse {
    private Long postId;
    private String title;
    private String content;
    private String author;
    private String createDate;
    private Category postCategory;
    private S3Info image;

    public static SavedTicleResponse toDto(Scrapped scrapped) {
        Post post = scrapped.getPost();
        return SavedTicleResponse.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor())
                .createDate(scrapped.getCreatedDate().format(DateTimeFormatter.ISO_DATE_TIME))
                .postCategory(post.getCategory())
                .image(post.getImage())
                .build();
    }

}
