package com.kts.taxify.exception;

public class NoParkingException extends CustomRuntimeException {
    public NoParkingException() {
        super(ExceptionKeys.NO_PARKING);
    }
}
