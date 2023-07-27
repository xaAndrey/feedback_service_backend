package com.feedback_service.feedback_service.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    public String issuer = "VISDOM";
    public String accessSecret;
    public String refreshSecret;
    public Long accessTokenExpirationTime = 3600000L;
    public Long refreshTokenExpirationTime = 7200000L;
}
