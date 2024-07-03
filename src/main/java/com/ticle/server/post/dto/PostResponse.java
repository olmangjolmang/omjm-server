package com.ticle.server.post.dto;

import com.ticle.server.post.domain.Post;
import com.ticle.server.global.domain.S3Info;
import com.ticle.server.user.domain.type.Category;
import lombok.Getter;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PostResponse {

    private Long postId;
    private String title;
    private String content;
    private String author;
    private LocalDateTime createdDate;
    private Category postCategory;
    private S3Info image;
    private Long userId;

    public static PostResponse from(Post post) {
        return new PostResponse(
                post.getPostId(),
                post.getTitle(),
                post.getContent(),
                post.getAuthor(),
                post.getCreatedDate(),
                post.getCategory(),
                post.getImage(),
                post.getUser() != null ? post.getUser().getId() : null
        );
    }
}
