package com.feedback_service.feedback_service.controller.error;

import com.feedback_service.feedback_service.exception.ResponseError;
import com.feedback_service.feedback_service.util.error.ErrorCode;
import com.feedback_service.feedback_service.util.error.ResolvedErrorResponse;
import com.feedback_service.feedback_service.util.error.ValidationErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.context.properties.bind.validation.ValidationErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ErrorController {
    @ExceptionHandler
    public ResponseEntity<ResolvedErrorResponse> responseErrorExceptionHandler(
            HttpServletRequest request,
            ResponseError exception
    ) {
        System.err.println("Resolved : [" + exception.getErrorCode() + "] with status: [" + exception.getStatus() + "] and message: [" + exception.getMessage() + "] ");
        ResolvedErrorResponse response = new ResolvedErrorResponse(
                exception.getMessage(),
                exception.getStatus(),
                exception.getStatus().value(),
                exception.getErrorCode(),
                request.getContextPath() + request.getServletPath());
        return new ResponseEntity<>(response, exception.getStatus());
    }

    @ExceptionHandler
    public ResponseEntity<ResolvedErrorResponse> responseDeniedExceptionHandler(
            HttpServletRequest request,
            AccessDeniedException exception
    ) {
        ResolvedErrorResponse response = new ResolvedErrorResponse(
                exception.getMessage(),
                HttpStatus.FORBIDDEN,
                HttpStatus.FORBIDDEN.value(),
                HttpStatus.FORBIDDEN.getReasonPhrase(),
                request.getContextPath() + request.getServletPath()
        );
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public ResponseEntity<ValidationErrorResponse> methodArgumentNotValidExceptionHandler(
            HttpServletRequest request,
            MethodArgumentNotValidException exception
    ) {
        List<String> validationErrorsList = exception.getBindingResult().getAllErrors().stream()
                .filter(e -> e instanceof FieldError)
                .map(e -> (FieldError) e)
                .map(e -> e.getField() + ":" + e.getDefaultMessage())
                .toList();

        ValidationErrorResponse response = new ValidationErrorResponse(
                "Request body validation failed",
                validationErrorsList,
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                request.getContextPath() + request.getServletPath()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ResolvedErrorResponse> unexpectedExceptionHandler(
            HttpServletRequest request,
            Throwable exception
    ) {
        System.err.println(exception.getMessage());
        ResolvedErrorResponse response = new ResolvedErrorResponse(
                exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ErrorCode.UNRESOLVED,
                request.getContextPath() + request.getServletPath()
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
