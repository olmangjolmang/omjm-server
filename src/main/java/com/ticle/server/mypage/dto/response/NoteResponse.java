package com.ticle.server.mypage.dto.response;

import com.ticle.server.global.dto.PageInfo;
import com.ticle.server.memo.domain.Memo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor
@Builder
public class NoteResponse {

    PageInfo pageInfo;
    private Long noteId;
    private String content;
    private String memoDate;
    private Long postId;
    private String postTitle;
    private String targetText;

    public static NoteResponse toDto(Memo memo,PageInfo pageInfo){
        return NoteResponse.builder()
                .pageInfo(pageInfo)
                .noteId(memo.getMemoId())
                .content(memo.getContent())
                .memoDate(memo.getCreatedDate().format(DateTimeFormatter.ISO_DATE_TIME))
                .postId(memo.getPost().getPostId())
                .postTitle(memo.getPost().getTitle())
                .targetText(memo.getTargetText())
                .build();
    }
}
