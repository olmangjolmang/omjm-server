package com.ticle.server.post.exception.errorcode;

import com.ticle.server.global.exception.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PostErrorCode implements ErrorCode {
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "찾을 수 없는 아티클입니다"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
