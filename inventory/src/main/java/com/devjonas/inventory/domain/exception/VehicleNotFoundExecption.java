package com.devjonas.inventory.domain.exception;

public class VehicleNotFoundExecption extends RuntimeException {
    public VehicleNotFoundExecption() {
        super("Vehicle not found.");
    }
}
