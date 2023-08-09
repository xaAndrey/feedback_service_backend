package com.feedback_service.feedback_service.configuration.jwt;

import com.feedback_service.feedback_service.configuration.jwt.token.TokenClaims;
import com.feedback_service.feedback_service.configuration.jwt.token.TokenType;
import com.feedback_service.feedback_service.service.user.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final String AUTHORIZATION = "Authorization";
    private JwtProvider jwtProvider;
    private UserDetailsServiceImpl userDetailsService;

    public JwtFilter(JwtProvider jwtProvider, UserDetailsServiceImpl userDetailsService) {
        this.jwtProvider = jwtProvider;
        this.userDetailsService = userDetailsService;
    }

    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String token = getTokenFromRequest(request);
        if (token != null) {
            if (jwtProvider.validateToken(token, TokenType.ACCESS_TOKEN)) {
                TokenClaims accessTokenClaims = jwtProvider.getTokenClaims(token, TokenType.ACCESS_TOKEN);
                JwtUserDetails userDetails = userDetailsService.loadUserById(accessTokenClaims.getId());
                if (checkUser(accessTokenClaims, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        } else {
            return null;
        }
    }

    private Boolean checkUser(TokenClaims accessTokenClaims, JwtUserDetails userDetails) {
        return Objects.equals(accessTokenClaims.securityStamp, userDetails.getSecurityStamp()) &&
                accessTokenClaims.tokenType == TokenType.ACCESS_TOKEN &&
                userDetails.isAccountNonLocked();
    }

}
