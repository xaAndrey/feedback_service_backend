package com.feedback_service.feedback_service.configuration.jwt.token;

public enum TokenType {
    ACCESS_TOKEN("ACCESS_TOKEN"),
    REFRESH_TOKEN("REFRESH_TOKEN"),
    UNKNOWN_TOKEN("UNKNOWN_TOKEN_TYPE");

    private String type;

    TokenType(String type) {
        this.type = type;
    }

    public String getType() { return this.type; }
}
