package com.kts.taxify.exception;

public class AccountNotLocalException extends CustomRuntimeException {
    public AccountNotLocalException() {
        super(ExceptionKeys.ACCOUNT_NOT_LOCAL);
    }
}
