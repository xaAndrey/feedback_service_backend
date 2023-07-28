package com.feedback_service.feedback_service.configuration.jwt.token;

public class TokenClaims {
    public Integer id;
    public String securityStamp;
    public TokenType tokenType;

    public TokenClaims(Integer id,
                       String securityStamp,
                       TokenType tokenType) {
        this.id = id;
        this.securityStamp = securityStamp;
        this.tokenType = tokenType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
