package com.ticle.server.global.dto;

import lombok.Builder;

import java.util.Collections;

@Builder
public record ResponseTemplate<T>(
        Boolean isSuccess,
        String code,
        String message,
        T results
) {
    public static final ResponseTemplate<Object> EMPTY_RESPONSE = ResponseTemplate.builder()
            .isSuccess(true)
            .code("REQUEST_OK")
            .message("요청이 승인되었습니다.")
            .results(Collections.emptyMap())
            .build();

    public static <T> ResponseTemplate<Object> from(T dto) {
        return ResponseTemplate.builder()
                .isSuccess(true)
                .code("REQUEST_OK")
                .message("요청이 승인되었습니다.")
                .results(dto)
                .build();
    }
}