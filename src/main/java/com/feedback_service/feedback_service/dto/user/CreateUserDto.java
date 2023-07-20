package com.feedback_service.feedback_service.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateUserDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
