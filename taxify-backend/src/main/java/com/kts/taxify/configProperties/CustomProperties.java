package com.kts.taxify.configProperties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import lombok.Data;

@ConfigurationProperties(prefix = "custom")
@ConfigurationPropertiesScan
@Data
public class CustomProperties {
	private String jwtSecret;
	private Long authTokenExpirationMilliseconds;
}
