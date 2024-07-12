package com.ticle.server.mypage.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateCommentDto {
    private String content;

    @Builder
    public UpdateCommentDto(String content){
        this.content = content;
    }
}
