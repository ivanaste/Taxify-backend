package com.kts.taxify.exception;

public class UnauthorizedException extends CustomRuntimeException {
	public UnauthorizedException() {
		super(ExceptionKeys.UNAUTHORIZED);
	}
}
