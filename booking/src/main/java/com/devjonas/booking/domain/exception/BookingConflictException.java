package com.devjonas.booking.domain.exception;

public class BookingConflictException extends RuntimeException {

    public BookingConflictException() {
        super("Vehicle already reserved during this period.");
    }
}
