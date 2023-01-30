package com.kts.taxify.exception;

public class NoActiveDriversException extends CustomRuntimeException {

	public NoActiveDriversException() {
		super(ExceptionKeys.NO_ACTIVE_DRIVERS);
	}

}
