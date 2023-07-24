package com.feedback_service.feedback_service.dto.registration;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateRegistrationDto {
    private String fio;
    private String phone;
    private String doctor;
    private String comments;
}
