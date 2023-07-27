package com.feedback_service.feedback_service.dto.registration;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateRegistrationDto {
    @NotNull
    private Boolean isRegistered;
}
