package com.feedback_service.feedback_service.configuration.jwt.request;

import jakarta.validation.constraints.NotBlank;

public class AuthRequest {
    @NotBlank
    public String login;
    @NotBlank
    public String password;
}
