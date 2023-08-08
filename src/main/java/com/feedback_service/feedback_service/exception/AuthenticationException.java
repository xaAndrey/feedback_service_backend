package com.feedback_service.feedback_service.exception;

import org.springframework.http.HttpStatus;

public class AuthenticationException extends RuntimeException {
    private String message;
    private HttpStatus status;
    private String errorCode;

    public AuthenticationException(
            String message,
            HttpStatus status,
            String errorCode
    ) {
        this.message = message;
        this.status = status;
        this.errorCode = errorCode;
    }

    public static void authenticationError(
            String message,
            HttpStatus status,
            String errorCode
    ) throws AuthenticationException {
        if (message != null &&
                status != null &&
                errorCode != null) {
            throw new AuthenticationException(message, status, errorCode);
        } else if (message != null) {
            throw new AuthenticationException(message, HttpStatus.UNAUTHORIZED, "IUC");
        } else {
            throw new AuthenticationException("[UNAUTHORIZED] Invalid user credentials", HttpStatus.UNAUTHORIZED, "IUC");
        }
    }

    public static void authenticationError() {
        throw new AuthenticationException("[UNAUTHORIZED] Invalid user credentials", HttpStatus.UNAUTHORIZED, "IUC");
    }

}
