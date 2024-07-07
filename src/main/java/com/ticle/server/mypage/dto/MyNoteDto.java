package com.ticle.server.mypage.dto;

import com.ticle.server.memo.domain.Memo;
import com.ticle.server.opinion.domain.Opinion;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MyNoteDto {
    private Long memoId;
    private String content;
    private LocalDateTime memoDate;
    private Long postId;
    private String postTitle;

    public static MyNoteDto toDto(Memo memo){
        return MyNoteDto.builder()
                .memoId(memo.getMemoId())
                .content(memo.getContent())
                .memoDate(memo.getCreatedDate())
                .postId(memo.getPost().getPostId())
                .postTitle(memo.getPost().getTitle())
                .build();
    }
}
