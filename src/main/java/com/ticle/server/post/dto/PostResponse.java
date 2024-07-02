package com.ticle.server.post.dto;

import com.ticle.server.post.domain.Post;
import com.ticle.server.global.domain.S3Info;
import com.ticle.server.mypage.domain.type.Category;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponse {

    private Long postId;
    private String title;
    private String content;
    private String author;
    private LocalDateTime createdDate;
    private Category postCategory;
    private S3Info image;
    private Long userId;

    public PostResponse(Post post) {
        this.postId = post.getPost_id();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.author = post.getAuthor();
        this.createdDate = post.getCreated_date();
        this.postCategory = post.getCategory();
        this.image = post.getImage();
        this.userId = post.getUser() != null ? post.getUser().getUserId() : null;
    }
}
