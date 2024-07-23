package com.ticle.server.home.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ticle.server.post.domain.Post;
import com.ticle.server.user.domain.type.Category;

import java.util.Date;

public record PostSetsResponse(
        String title,
        String imageUrl,
        Category category,
        String author,
        @JsonFormat(pattern = "yyyy-MM-dd")
        Date createdDate
) {
    public static PostSetsResponse from(Post post) {
        return new PostSetsResponse(
                post.getTitle(),
                post.getImage().getImageUrl(),
                post.getCategory(),
                post.getAuthor(),
                post.getCreatedDate()
        );
    }
}
