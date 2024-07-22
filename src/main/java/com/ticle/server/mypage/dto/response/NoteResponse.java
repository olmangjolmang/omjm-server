package com.ticle.server.mypage.dto.response;

import com.ticle.server.memo.domain.Memo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class NoteResponse {
    private Long noteId;
    private String content;
    private LocalDateTime memoDate;
    private Long postId;
    private String postTitle;
    private String targetText;

    public static NoteResponse toDto(Memo memo){
        return NoteResponse.builder()
                .noteId(memo.getMemoId())
                .content(memo.getContent())
                .memoDate(memo.getCreatedDate())
                .postId(memo.getPost().getPostId())
                .postTitle(memo.getPost().getTitle())
                .targetText(memo.getTargetText())
                .build();
    }
}
