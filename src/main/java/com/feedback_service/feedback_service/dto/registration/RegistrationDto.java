package com.feedback_service.feedback_service.dto.registration;

import com.feedback_service.feedback_service.dto.user.UserDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class RegistrationDto {
    private Integer id;

    private String fio;

    private String phone;

    private String doctor;

    private Date dateRegistration;

    private boolean isRegistered;

    private String comments;

    private UserDto userDto;
}