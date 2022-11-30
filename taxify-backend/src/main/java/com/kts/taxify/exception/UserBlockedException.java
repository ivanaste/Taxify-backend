package com.kts.taxify.exception;

public class UserBlockedException extends CustomRuntimeException {
	public UserBlockedException() {
		super(ExceptionKeys.USER_BLOCKED);
	}
}
