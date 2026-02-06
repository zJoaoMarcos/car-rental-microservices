package com.devjonas.booking.application.usecase;

import com.devjonas.booking.application.client.VehicleServiceClient;
import com.devjonas.booking.domain.entities.Booking;
import com.devjonas.booking.application.dto.RegisterBookingDTO;
import com.devjonas.booking.application.dto.VehicleDTO;
import com.devjonas.booking.domain.event.BookingCreatedEvent;
import com.devjonas.booking.domain.exception.VehicleNotFoundException;
import com.devjonas.booking.infra.messaging.BookingEventPublisher;
import com.devjonas.booking.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class ReserveVehicle {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private VehicleServiceClient vehicleServiceClient;

    @Autowired
    private BookingEventPublisher bookingEventPublisher;

    public Booking execute(RegisterBookingDTO request) {

        VehicleDTO vehicle = vehicleServiceClient.getVehicleById(request.vehicleId());

        if(vehicle == null) {
            throw new VehicleNotFoundException();
        }

        Booking booking = Booking.create(vehicle, request.clientId(), request.startDate(), request.endDate());

        bookingRepository.save(booking);
        bookingEventPublisher.publish(new BookingCreatedEvent(
                booking.getId(),
                booking.getClientId(),
                booking.getVehicleId(),
                LocalDateTime.now()
        ));

        return booking;
    }
}
