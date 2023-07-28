package com.feedback_service.feedback_service.service.user;

import com.feedback_service.feedback_service.configuration.jwt.JwtUserDetails;
import com.feedback_service.feedback_service.entity.user.UserEntity;
import com.feedback_service.feedback_service.exception.UserNotFoundException;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserService userService;

    public UserDetailsServiceImpl(@Lazy UserService userService) {
        this.userService = userService;
    }

    @Override
    public JwtUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userService.findUserEntityByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User with username [" + username + "] not found.");
        }

        return new JwtUserDetails(user, Collections.emptyList());
    }

    public JwtUserDetails loadUserById(Integer id) throws UserNotFoundException {
        UserEntity user = userService.findUserEntityById(id);
        if (user == null) {
            throw new UserNotFoundException("User with ID [" + id + "] not found.");
        }

        return new JwtUserDetails(user, Collections.emptyList());
    }
}