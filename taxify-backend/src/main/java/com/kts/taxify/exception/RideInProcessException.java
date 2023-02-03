package com.kts.taxify.exception;

public class RideInProcessException extends CustomRuntimeException{
    public RideInProcessException(){super(ExceptionKeys.RIDE_IN_PROCESS);}
}
