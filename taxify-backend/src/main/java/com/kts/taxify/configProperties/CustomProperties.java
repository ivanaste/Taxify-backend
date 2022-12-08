package com.kts.taxify.configProperties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationProperties(prefix = "custom")
@ConfigurationPropertiesScan
@Data
public class CustomProperties {
    private String jwtSecret;
    private Long authTokenExpirationMilliseconds;
    private String messageSource;
    private String defaultLocale;
    private String senderEmail;
    private String clientUrl;
    private Long jwtForgotPasswordTokenExpiration;
    private Long jwtActivateEmailTokenExpiration;
}
