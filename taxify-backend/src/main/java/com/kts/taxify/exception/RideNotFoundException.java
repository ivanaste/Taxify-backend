package com.kts.taxify.exception;

public class RideNotFoundException extends CustomRuntimeException {
	public RideNotFoundException() {
		super(ExceptionKeys.RIDE_NOT_FOUND);
	}

}
