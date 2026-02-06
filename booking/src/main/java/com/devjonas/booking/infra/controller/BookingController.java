package com.devjonas.booking.infra.controller;

import com.devjonas.booking.application.dto.RegisterBookingDTO;
import com.devjonas.booking.application.usecase.ReserveVehicle;
import com.devjonas.booking.domain.exception.VehicleNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/bookings")
public class BookingController {

    @Autowired
    private ReserveVehicle reserveVehicle;

    @PostMapping
    public ResponseEntity<Object> reserve(@RequestBody RegisterBookingDTO request) {
        try {
            return ResponseEntity.ok().body(reserveVehicle.execute(request));
        } catch (VehicleNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
