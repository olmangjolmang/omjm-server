package com.ticle.server.mypage.dto.response;

import com.ticle.server.global.dto.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QnAResponse {

    private String question;
    private Long questionId;
    private String comment;
    private String createdDate;
    private PageInfo pageInfo;
}
