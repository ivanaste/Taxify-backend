package com.kts.taxify.exception;

public class PasswordSameException extends CustomRuntimeException {
    public PasswordSameException() {
        super(ExceptionKeys.PASSWORD_SAME);
    }
}
