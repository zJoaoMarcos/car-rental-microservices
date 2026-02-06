package com.devjonas.booking.domain.exception;

public class InvalidBookingDatesException extends RuntimeException {
    public InvalidBookingDatesException() {
        super("Invalid booking date Exception");
    }
}
