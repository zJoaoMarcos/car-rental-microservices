package com.devjonas.inventory.domain.exception;

public class PlateAlreadyExistsException extends RuntimeException {
    public PlateAlreadyExistsException() {
        super("Vehicle with this plate already exists.");
    }
}
