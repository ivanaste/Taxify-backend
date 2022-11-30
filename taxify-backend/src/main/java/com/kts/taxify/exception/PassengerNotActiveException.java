package com.kts.taxify.exception;

public class PassengerNotActiveException extends CustomRuntimeException {
	public PassengerNotActiveException() {
		super(ExceptionKeys.USER_NOT_ACTIVE);
	}
}
