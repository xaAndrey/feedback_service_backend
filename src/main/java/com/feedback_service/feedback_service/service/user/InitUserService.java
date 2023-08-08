package com.feedback_service.feedback_service.service.user;

import com.feedback_service.feedback_service.dto.user.CreateUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

import static com.feedback_service.feedback_service.util.SequenceGenerator.generatePassword;

@Service
public class InitUserService {

    private final UserService userService;
    private static final Logger logger = Logger.getLogger(InitUserService.class.getName());

    @Autowired
    public InitUserService(UserService userService) {
        this.userService = userService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initUser() {
        if (!userService.anyUserExists()) {
            logger.info("Initializing default admin user");
            String password = generatePassword(20);
            CreateUserDto newUser = new CreateUserDto(
                    "administrator",
                    password
            );
            userService.createUserEntity(newUser);
            logger.info("Created new admin user with username: [" + newUser.getUsername() + "] and password: [" + newUser.getPassword() + "]");
        } else {
            logger.warning("First boot initialization feature can be disabled, because database already contains users");
        }
    }
}
