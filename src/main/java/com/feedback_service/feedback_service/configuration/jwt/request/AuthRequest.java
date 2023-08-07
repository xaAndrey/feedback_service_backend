package com.feedback_service.feedback_service.configuration.jwt.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthRequest {
    @NotBlank
    public String login;
    @NotBlank
    public String password;
}
