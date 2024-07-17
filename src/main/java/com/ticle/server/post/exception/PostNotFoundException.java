package com.ticle.server.post.exception;

import com.ticle.server.global.exception.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PostNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;
}
