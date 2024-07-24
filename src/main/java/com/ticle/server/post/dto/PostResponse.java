package com.ticle.server.post.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ticle.server.global.domain.S3Info;
import com.ticle.server.post.domain.Post;
import com.ticle.server.user.domain.type.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {

    private Long postId;
    private String title;
    private String content;
    private String author;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdDate;
    private Category postCategory;
    private S3Info image;
//    private List recommendPost;

    public static PostResponse from(Post post) {
        return new PostResponse(
                post.getPostId(),
                post.getTitle(),
                post.getContent(),
                post.getAuthor(),
                post.getCreatedDate(),
                post.getCategory(),
                post.getImage()
//                post.getRecommendPost()
        );
    }
}
