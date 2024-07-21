package com.ticle.server.user.exception;

public class InvalidPasswordException extends RuntimeException{

    public InvalidPasswordException(String message){
        super(message);
    }
}
