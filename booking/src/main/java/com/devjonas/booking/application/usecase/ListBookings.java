package com.devjonas.booking.application.usecase;

import com.devjonas.booking.application.dto.ListBookingsRequestDTO;
import com.devjonas.booking.domain.entities.Booking;
import com.devjonas.booking.repository.BookingRepository;
import com.devjonas.booking.repository.BookingSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListBookings {

    @Autowired
    private BookingRepository bookingRepository;

    public List<Booking> execute(ListBookingsRequestDTO request) {

        return bookingRepository.findAll(Specification.where(BookingSpec.withCustomerId(request.customerId())
                .and(BookingSpec.withVehicleId(request.vehicleId()))));
    }

}
