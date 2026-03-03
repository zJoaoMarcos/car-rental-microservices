package com.devjonas.booking.application.usecase;

import com.devjonas.booking.application.client.VehicleServiceClient;
import com.devjonas.booking.domain.entities.Booking;
import com.devjonas.booking.application.dto.CreateBookingRequestDTO;
import com.devjonas.booking.application.dto.VehicleDTO;
import com.devjonas.booking.domain.event.BookingCreatedEvent;
import com.devjonas.booking.domain.exception.BookingConflictException;
import com.devjonas.booking.domain.exception.VehicleNotFoundException;
import com.devjonas.booking.infra.messaging.BookingCreatedEventPublisher;
import com.devjonas.booking.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CreateBooking {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private VehicleServiceClient vehicleServiceClient;

    @Autowired
    private BookingCreatedEventPublisher bookingEventPublisher;

    public Booking execute(CreateBookingRequestDTO request) throws VehicleNotFoundException {

        VehicleDTO vehicle = vehicleServiceClient.getVehicleById(request.vehicleId());

        if(vehicle == null) {
            throw new VehicleNotFoundException();
        }

        Booking booking = Booking.create(vehicle, request.customerId(), request.startDate(), request.endDate());

        List<Booking> conflicts = bookingRepository.findConflictingBookings(vehicle.id(), booking.getStartDate(), booking.getEndDate());

        if (!conflicts.isEmpty()) {
            throw new BookingConflictException();
        }

        Booking saved = bookingRepository.save(booking);
        bookingEventPublisher.publish(new BookingCreatedEvent(
                saved.getId(),
                saved.getCustomerId(),
                saved.getVehicleId(),
                LocalDateTime.now()
        ));

        return booking;
    }
}
