package com.kts.taxify.exception;

import lombok.Getter;

@Getter
public enum ExceptionKeys {
	USER_NOT_FOUND("user_not_found"),
	USER_NOT_ACTIVE("user_not_active"),

	USER_BLOCKED("user_blocked"),
	UNAUTHORIZED("unauthorized"),
	AUTH_TOKEN_EXPIRED("authentication_token_expired"),

	BAD_LOGIN_CREDENTIALS("bed_login_credentials"),

	USER_ALREADY_EXISTS("user_already_exists");

	private final String code;

	private String value;

	ExceptionKeys(final String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return value;
	}
}
