package com.ticle.server.opinion.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import com.ticle.server.global.exception.errorcode.ErrorCode;

@Getter
@RequiredArgsConstructor
public enum OpinionErrorCode implements ErrorCode {
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "찾을 수 없는 댓글입니다"),
    OPINION_NOT_FOUND(HttpStatus.NOT_FOUND, "찾을 수 없는 질문입니다"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
