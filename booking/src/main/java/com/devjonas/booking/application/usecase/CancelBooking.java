package com.devjonas.booking.application.usecase;

import com.devjonas.booking.application.dto.CancelBookingResponseDTO;
import com.devjonas.booking.domain.entities.Booking;
import com.devjonas.booking.domain.event.BookingCancelledEvent;
import com.devjonas.booking.domain.exception.BookingNotFoundException;
import com.devjonas.booking.infra.messaging.BookingCancelledEventPublisher;
import com.devjonas.booking.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CancelBooking {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BookingCancelledEventPublisher bookingCancelledEventPublisher;

    public CancelBookingResponseDTO execute(UUID bookingId) {

        Booking booking = bookingRepository.findById(bookingId).orElseThrow(BookingNotFoundException::new);

        bookingRepository.delete(booking);

        bookingCancelledEventPublisher.publish(new BookingCancelledEvent(
                booking.getId(),
                booking.getCustomerId(),
                booking.getVehicleId(),
                LocalDateTime.now()
        ));

        return new CancelBookingResponseDTO(bookingId);
    }
}
