package com.kts.taxify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableScheduling
@ConfigurationPropertiesScan("com.kts.taxify.configProperties")
public class TaxifyApplication {
	public static void main(final String[] args) {
		SpringApplication.run(TaxifyApplication.class, args);
	}
}
