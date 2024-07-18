package com.ticle.server.mypage.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NoteUpdateRequest {

    private String content;

    @Builder
    public NoteUpdateRequest(String content){
        this.content = content;
    }

}
