package com.feedback_service.feedback_service.configuration.jwt.token;

public class TokenClaims {
    public Long id;
    public String securityStamp;
    public TokenType tokenType;

    public TokenClaims(Long id,
                       String securityStamp,
                       TokenType tokenType) {
        this.id = id;
        this.securityStamp = securityStamp;
        this.tokenType = tokenType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSecurityStamp() {
        return securityStamp;
    }

    public void setSecurityStamp(String securityStamp) {
        this.securityStamp = securityStamp;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }
}
