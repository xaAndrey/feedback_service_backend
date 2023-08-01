package com.feedback_service.feedback_service.service.auth;

import com.feedback_service.feedback_service.configuration.jwt.JwtProvider;
import com.feedback_service.feedback_service.configuration.jwt.request.AuthRequest;
import com.feedback_service.feedback_service.configuration.jwt.request.RefreshRequest;
import com.feedback_service.feedback_service.configuration.jwt.token.TokenClaims;
import com.feedback_service.feedback_service.configuration.jwt.token.TokenPair;
import com.feedback_service.feedback_service.configuration.jwt.token.TokenType;
import com.feedback_service.feedback_service.entity.user.UserEntity;
import com.feedback_service.feedback_service.exception.AuthenticationException;
import com.feedback_service.feedback_service.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.rmi.AccessException;
import java.util.Objects;

@Service
public class AuthorizationService {
    private UserService userService;
    private JwtProvider jwtProvider;
    private PasswordEncoder passwordEncoder;

    public AuthorizationService(
            UserService userService,
            JwtProvider jwtProvider,
            PasswordEncoder passwordEncoder
    ) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
    }

    public TokenPair authAndGenerateTokenPair(AuthRequest request) {
        String login = request.login;
        String password = request.password;
        UserEntity user = userService.findUserEntityByUsername(login);
        if (user == null) {
            AuthenticationException.authenticationError();
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            AuthenticationException.authenticationError();
        }
        if (user.isAccountLocked()) {
            AuthenticationException.authenticationError("Account with ID [" + user.getId() + "] is locked", null, null);
        }

        return jwtProvider.generateTokenPair(user);
    }

    public TokenPair refreshTokenPair(RefreshRequest request) {
        String refreshToken = request.refreshToken;
        if (!jwtProvider.validateToken(refreshToken, TokenType.REFRESH_TOKEN)) {
            AuthenticationException.authenticationError();
        }

        TokenClaims accessTokenClaims = jwtProvider.getTokenClaims(refreshToken, TokenType.REFRESH_TOKEN);
        UserEntity user = userService.findUserEntityById(accessTokenClaims.id);
        if (user == null) {
            AuthenticationException.authenticationError();
        }

        if (!Objects.equals(accessTokenClaims.securityStamp, user.getSecurityStamp())) {
            AuthenticationException.authenticationError();
        }

        if (user.isAccountLocked()) {
            AuthenticationException.authenticationError("Account with ID [" + user.getId() + "] is locked", null, null);
        }

        return jwtProvider.generateTokenPair(user);
    }
}
