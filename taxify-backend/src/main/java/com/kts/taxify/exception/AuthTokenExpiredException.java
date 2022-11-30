package com.kts.taxify.exception;

public class AuthTokenExpiredException extends CustomRuntimeException {
	public AuthTokenExpiredException() {
		super(ExceptionKeys.AUTH_TOKEN_EXPIRED);
	}
}
