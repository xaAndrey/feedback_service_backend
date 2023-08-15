package com.feedback_service.feedback_service.util.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ValidationErrorResponse {
    private String message;
    private List<String> validationErrors;
    private HttpStatus status;
    private Integer statusCode;
    private String errorCode;
    private String path;
}
