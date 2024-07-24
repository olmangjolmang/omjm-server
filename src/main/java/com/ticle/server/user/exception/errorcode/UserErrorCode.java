package com.ticle.server.user.exception.errorcode;

import com.ticle.server.global.exception.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다"),
    USER_NOT_LOGIN(HttpStatus.UNAUTHORIZED, "로그인하지 않은 유저입니다"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
