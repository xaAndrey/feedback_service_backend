package com.feedback_service.feedback_service.dto.user;

import com.feedback_service.feedback_service.entity.registration.RegistrationEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    private Integer id;
    private String username;
    private String password;
    private String securityStamp;
    private ZonedDateTime registrationDate;
    private boolean accountLocked;
}
