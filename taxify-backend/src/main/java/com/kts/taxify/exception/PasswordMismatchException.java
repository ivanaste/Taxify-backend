package com.kts.taxify.exception;

public class PasswordMismatchException extends CustomRuntimeException {
    public PasswordMismatchException() {
        super(ExceptionKeys.PASSWORD_MISMATCH);
    }
}
