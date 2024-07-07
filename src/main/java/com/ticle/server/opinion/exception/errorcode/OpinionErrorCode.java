package com.ticle.server.opinion.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import com.ticle.server.global.exception.errorcode.ErrorCode;

@Getter
@RequiredArgsConstructor
public enum OpinionErrorCode implements ErrorCode {
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "Comment not found"),
    OPINION_NOT_FOUND(HttpStatus.NOT_FOUND, "Opinion not found"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
