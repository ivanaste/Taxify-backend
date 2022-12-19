package com.kts.taxify.services.jwt;

import com.kts.taxify.configProperties.CustomProperties;

import java.util.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class JwtGenerateToken {
	private final CustomProperties customProperties;

	public String execute(final String email, final long expirationMilliseconds) {
		return Jwts.builder()
			.setSubject(email)
			.setExpiration(new Date(new Date().getTime() + expirationMilliseconds))
			.setIssuedAt(new Date())
			.signWith(SignatureAlgorithm.HS512, customProperties.getJwtSecret().getBytes())
			.compact();
	}
}
