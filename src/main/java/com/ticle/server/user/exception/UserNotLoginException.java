package com.ticle.server.user.exception;

import com.ticle.server.global.exception.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserNotLoginException extends RuntimeException {

    private final ErrorCode errorCode;
}