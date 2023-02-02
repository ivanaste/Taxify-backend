package com.kts.taxify.exception;

public class InvalidPaymentForSharedRideException extends CustomRuntimeException {
    public InvalidPaymentForSharedRideException() {
        super(ExceptionKeys.INVALID_PAYMENT_FOR_SHARED_RIDE);
    }
}
