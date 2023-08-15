package com.feedback_service.feedback_service.util.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResolvedErrorResponse {
    private String message;
    private HttpStatus status;
    private Integer statusCode;
    private String errorCode;
    private String path;
}
