package com.devjonas.booking.domain.exception;

public class BookingInvalidatePeriodException extends RuntimeException {
    public BookingInvalidatePeriodException() {
        super("Invalid booking date Exception");
    }
}
