package com.kts.taxify.exception;

public class VehicleNotFoundException extends CustomRuntimeException {
    public VehicleNotFoundException() {
        super(ExceptionKeys.VEHICLE_NOT_FOUND);
    }
}
