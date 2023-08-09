package com.feedback_service.feedback_service.exception;

import com.feedback_service.feedback_service.util.error.ErrorCode;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends ResponseError {

    public UserNotFoundException(
            String message,
            HttpStatus status,
            String errorCode) {
        super(message, status, errorCode);
    }

    public static void registrationNotFoundError(
            String message,
            HttpStatus status,
            String errorCode
    ) {
        if (message != null &&
                status != null &&
                errorCode != null) {
            throw new RegistrationNotFoundException(message, status, errorCode);
        } else if (message != null) {
            throw new RegistrationNotFoundException(message, HttpStatus.NOT_FOUND, ErrorCode.UNF);
        }
    }
}

