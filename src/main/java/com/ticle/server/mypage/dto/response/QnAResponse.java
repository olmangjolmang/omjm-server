package com.ticle.server.mypage.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QnAResponse {

    private String question;
    private String comment;
    private String createdDate;
}
