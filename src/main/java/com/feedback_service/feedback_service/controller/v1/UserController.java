package com.feedback_service.feedback_service.controller.v1;

import com.feedback_service.feedback_service.dto.user.CreateUserDto;
import com.feedback_service.feedback_service.dto.user.UserDto;
import com.feedback_service.feedback_service.exception.UserAlreadyExistException;
import com.feedback_service.feedback_service.exception.UserNotFoundException;
import com.feedback_service.feedback_service.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequestMapping(path = "/api/v1/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUser() {
        return ResponseEntity.ok(userService.findUserDtoAll());
    }

    @GetMapping(path = "/user/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(userService.findUserDtoById(id));
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping(path = "/user")
    public ResponseEntity<UserDto> createUser(
            @RequestBody CreateUserDto newUser
    ) throws ResponseStatusException
    {
        try {
            return ResponseEntity.ok(userService.createUserDto(newUser));
        } catch (UserAlreadyExistException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
