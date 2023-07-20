package com.feedback_service.feedback_service.exception;

public class UserAlreadyExistException extends RuntimeException{

    public UserAlreadyExistException(String msg) {
        super(msg);
    }
}

