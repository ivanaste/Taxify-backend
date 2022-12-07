package com.kts.taxify.exception;

public class InvalidGoogleAccountException extends CustomRuntimeException {
    public InvalidGoogleAccountException() {
        super(ExceptionKeys.INVALID_GOOGLE_ACCOUNT);
    }
}
