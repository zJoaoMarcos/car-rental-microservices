package com.devjonas.inventory.domain.exception;

public class FailureRegisterVehicleException extends RuntimeException {
    public FailureRegisterVehicleException() {
        super("Failure to register the vehicle.");
    }
}
