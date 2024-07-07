package com.ticle.server.memo.dto;

import com.ticle.server.memo.domain.Memo;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemoDto {


    private Long userId;
    private Long postId;
    private String targetText;
    private String content; // 스크랩 상태

    public static MemoDto from(Memo memo) {
        return MemoDto.builder()
                .userId(memo.getUser().getId())
                .postId(memo.getPost().getPostId())
                .targetText(memo.getTargetText())
                .content(memo.getContent())
                .build();
    }
}
