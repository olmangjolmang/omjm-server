package com.ticle.server.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostIsSavedResponse {
    private Long postId;
    private Boolean isScrapped;

    public static PostIsSavedResponse from(Long postId, Boolean isScrap) {
        return new PostIsSavedResponse(postId, isScrap);
    }
}
