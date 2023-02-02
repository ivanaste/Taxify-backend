package com.kts.taxify.exception;

public class AllDriversBusyAndReservedException extends CustomRuntimeException {
    public AllDriversBusyAndReservedException() {
        super(ExceptionKeys.DRIVES_BUSY_AND_RESERVED);
    }
}