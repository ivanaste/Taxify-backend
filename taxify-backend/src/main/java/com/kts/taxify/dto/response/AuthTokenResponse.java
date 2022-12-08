package com.kts.taxify.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthTokenResponse {
	String token;
	long expiresIn;
	String role;
}
