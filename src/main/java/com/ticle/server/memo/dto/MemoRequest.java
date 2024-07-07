package com.ticle.server.memo.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemoRequest {

    @NotBlank(message = "targetText는 필수 항목입니다.")
    private String targetText;

    @NotBlank(message = "content는 필수 항목입니다.")
    private String content;

}