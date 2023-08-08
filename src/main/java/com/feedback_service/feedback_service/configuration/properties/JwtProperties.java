package com.feedback_service.feedback_service.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private String issuer = "VISDOM";
    private String accessSecret;
    private String refreshSecret;
    private Long accessTokenExpirationTime = 3600000L;
    private Long refreshTokenExpirationTime = 7200000L;
}
