package com.ticle.server.home.dto.response;

import com.ticle.server.post.domain.Post;
import com.ticle.server.user.domain.type.Category;

import java.time.LocalDateTime;

public record PostSetsResponse(
        String title,
        String imageUrl,
        Category category,
        String author,
        LocalDateTime createdDate
) {
    public static PostSetsResponse of(Post post) {
        return new PostSetsResponse(
                post.getTitle(),
                post.getImage().getImageUrl(),
                post.getCategory(),
                post.getAuthor(),
                post.getCreatedDate()
        );
    }
}
