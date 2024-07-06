package com.ticle.server.opinion.exception;

import com.ticle.server.global.exception.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OpinionNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;
}
