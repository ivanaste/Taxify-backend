package com.kts.taxify.exception;

public class RoleNotFoundException extends CustomRuntimeException {
    public RoleNotFoundException() {
        super(ExceptionKeys.ROLE_NOT_FOUND);
    }
}