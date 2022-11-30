package com.kts.taxify.exception;

public class AuthTokenInvalidException extends CustomRuntimeException {
    public AuthTokenInvalidException() {
        super(ExceptionKeys.AUTH_TOKEN_INVALID);
    }
}
