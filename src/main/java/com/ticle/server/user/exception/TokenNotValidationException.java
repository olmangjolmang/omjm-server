package com.ticle.server.user.exception;

import io.jsonwebtoken.JwtException;

public class TokenNotValidationException extends JwtException {

    public TokenNotValidationException(String message){
        super(message);
    }

    public TokenNotValidationException(String message,Throwable cause){
        super(message,cause);
    }
}
