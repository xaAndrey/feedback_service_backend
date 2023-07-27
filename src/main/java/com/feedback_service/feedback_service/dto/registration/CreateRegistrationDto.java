package com.feedback_service.feedback_service.dto.registration;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateRegistrationDto {
    @NotBlank
    private String fio;
    @NotBlank
    private String phone;
    @NotBlank
    private String doctor;
    private String comments;
    @NotNull
    private Integer userId;
}
