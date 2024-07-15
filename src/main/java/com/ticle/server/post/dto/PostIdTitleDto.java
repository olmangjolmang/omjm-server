package com.ticle.server.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PostIdTitleDto {
    private Long postId;
    private String title;

    @Override
    public String toString() {
        return "{" +
                "postId=" + postId +
                ", title='" + title + '\'' +
                '}';
    }
}