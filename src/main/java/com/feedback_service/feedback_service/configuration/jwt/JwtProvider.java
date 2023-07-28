package com.feedback_service.feedback_service.configuration.jwt;

import com.feedback_service.feedback_service.configuration.jwt.token.TokenClaims;
import com.feedback_service.feedback_service.configuration.jwt.token.TokenPair;
import com.feedback_service.feedback_service.configuration.jwt.token.TokenType;
import com.feedback_service.feedback_service.configuration.properties.JwtProperties;
import com.feedback_service.feedback_service.entity.user.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.requireNonNull;

@Component
public class JwtProvider {
    private JwtProperties jwtProperties;

    public JwtProvider() {}

    public JwtProvider(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    public TokenPair generateTokenPair(UserEntity user) {
        String accessToken = generatedToken(user, jwtProperties.accessTokenExpirationTime, TokenType.ACCESS_TOKEN, jwtProperties.accessSecret);
        String refreshToken = generatedToken(user, jwtProperties.refreshTokenExpirationTime, TokenType.REFRESH_TOKEN, jwtProperties.refreshSecret);
        return new TokenPair(accessToken, jwtProperties.accessTokenExpirationTime, refreshToken, jwtProperties.refreshTokenExpirationTime);
    }

    public Boolean validateToken(String token, TokenType tokenType) {
        try {
            Jwts.parser().setSigningKey(getSecret(tokenType)).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public TokenClaims getTokenClaims(String token, TokenType targetTokenType) {
        String secret = getSecret(targetTokenType);
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

        return new TokenClaims(
                Long.parseLong(claims.get("ID", String.class)),
                claims.get("SECURITY_STAMP", String.class),
                toTokenType(claims.get("TOKEN_TYPE", String.class))
        );
    }

    private String generatedToken(UserEntity user,
                                  Long expirationTime,
                                  TokenType tokenType,
                                  String secret
    ) {
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);
        Map<String, Object> claims = new HashMap<>();
        claims.put("ID", requireNonNull(user.getId()).toString());
        claims.put("SECURITY_STAMP", user.getSecurityStamp());
        claims.put("TOKEN_TYPE", tokenType);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuer(jwtProperties.issuer)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .setHeaderParam("typ", "JWT")
                .compact();
    }

    private TokenType toTokenType(String extractedTokenType) {
        try {
            return TokenType.valueOf(extractedTokenType);
        } catch (IllegalArgumentException e) {
            return TokenType.UNKNOWN_TOKEN;
        }
    }

    private String getSecret(TokenType tokenType) {
        return switch (tokenType) {
            case ACCESS_TOKEN -> jwtProperties.accessSecret;
            case REFRESH_TOKEN -> jwtProperties.refreshSecret;
            default -> "";
        };
    }

}
