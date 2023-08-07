package com.feedback_service.feedback_service.service.user;

import com.feedback_service.feedback_service.configuration.jwt.JwtUserDetails;
import com.feedback_service.feedback_service.entity.user.UserEntity;
import com.feedback_service.feedback_service.exception.UserNotFoundException;
import com.feedback_service.feedback_service.util.error.ErrorCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserService userService;

    @Autowired
    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public JwtUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userService.findUserEntityByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User with username [" + username + "] not found.");
        }

        return new JwtUserDetails(user);
    }

    public JwtUserDetails loadUserById(Integer id) throws UserNotFoundException {
        UserEntity user = userService.findUserEntityById(id);
        if (user == null) {
            throw new UserNotFoundException("User with ID [" + id + "] not found.", HttpStatus.NOT_FOUND, ErrorCode.UNF);
        }

        return new JwtUserDetails(user);
    }
}
