package com.kts.taxify.exception;

public class PassengerAccountNotActivatedException extends CustomRuntimeException{
    public PassengerAccountNotActivatedException() {super(ExceptionKeys.PASSENGER_ACCOUNT_NOT_ACTIVATED);}
}
