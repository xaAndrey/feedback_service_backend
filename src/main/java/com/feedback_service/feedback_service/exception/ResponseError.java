package com.feedback_service.feedback_service.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseError extends RuntimeException {
    private String message;
    private HttpStatus status;
    private String errorCode;
}
