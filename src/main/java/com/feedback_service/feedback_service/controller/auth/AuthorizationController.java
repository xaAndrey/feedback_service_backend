package com.feedback_service.feedback_service.controller.auth;

import com.feedback_service.feedback_service.configuration.jwt.request.AuthRequest;
import com.feedback_service.feedback_service.configuration.jwt.request.RefreshRequest;
import com.feedback_service.feedback_service.configuration.jwt.token.TokenPair;
import com.feedback_service.feedback_service.service.auth.AuthorizationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthorizationController {
    private final AuthorizationService authorizationService;

    @Autowired
    public AuthorizationController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @PostMapping("/access")
    TokenPair auth(@Valid @RequestBody AuthRequest request) {
        return authorizationService.authAndGenerateTokenPair(request);
    }

    @PostMapping("/refresh")
    TokenPair refresh(@Valid @RequestBody RefreshRequest request) {
        return authorizationService.refreshTokenPair(request);
    }

}
