package com.ticle.server.scrapped.dto;

import com.ticle.server.scrapped.domain.Scrapped;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScrappedDto {

    private Long postId;
    private Long userId;
    private String postName;
    private String status; // 스크랩 상태

    public static ScrappedDto from(Scrapped scrapped) {
        return ScrappedDto.builder()
                .userId(scrapped.getUser().getId())
                .postId(scrapped.getPost().getPostId())
                .postName(scrapped.getPost().getTitle())
                .status("Scrapped")
                .build();
    }

}
