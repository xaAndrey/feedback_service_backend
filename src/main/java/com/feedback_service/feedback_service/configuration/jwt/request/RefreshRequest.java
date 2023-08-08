package com.feedback_service.feedback_service.configuration.jwt.request;

import jakarta.validation.constraints.NotEmpty;

public class RefreshRequest {
    @NotEmpty
    public String refreshToken;
}
