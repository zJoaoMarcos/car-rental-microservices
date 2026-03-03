package com.devjonas.booking.infra.controller;

import com.devjonas.booking.application.dto.CreateBookingRequestDTO;
import com.devjonas.booking.application.dto.ListBookingsRequestDTO;
import com.devjonas.booking.application.usecase.CancelBooking;
import com.devjonas.booking.application.usecase.ListBookings;
import com.devjonas.booking.application.usecase.CreateBooking;
import com.devjonas.booking.domain.exception.BookingNotFoundException;
import com.devjonas.booking.domain.exception.VehicleNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/bookings")
public class BookingController {

    @Autowired
    private CreateBooking reserveVehicle;

    @Autowired
    private ListBookings listBookings;

    @Autowired
    private CancelBooking cancelBooking;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody CreateBookingRequestDTO request) {
        try {
            return ResponseEntity.ok().body(reserveVehicle.execute(request));
        } catch (VehicleNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Object> list(@RequestParam(required = false) UUID customerId, @RequestParam(required = false) UUID vehicleId) {
        return ResponseEntity.ok().body(listBookings.execute(new ListBookingsRequestDTO(customerId, vehicleId)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> cancel(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok().body(cancelBooking.execute(id));
        } catch (BookingNotFoundException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}
