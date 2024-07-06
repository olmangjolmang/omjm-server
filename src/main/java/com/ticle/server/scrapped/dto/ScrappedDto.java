package com.ticle.server.scrapped.dto;

import com.ticle.server.scrapped.domain.Scrapped;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScrappedDto {
    
    private Long userId;
    private Long postId;
    private String postName;
    private String status;

    public static ScrappedDto from(Scrapped scrapped) {
        return ScrappedDto.builder()
                .userId(scrapped.getUser().getId())
                .postId(scrapped.getPost().getPostId())
                .postName(scrapped.getPost().getTitle())
                .status(scrapped.getStatus())
                .build();
    }

}
