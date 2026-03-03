package com.devjonas.booking.repository;

import com.devjonas.booking.domain.entities.Booking;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class BookingSpec {

    public static Specification<Booking> withVehicleId(UUID vehicleId) {
        return (root, query, cb) ->
                vehicleId == null ? null : cb.equal(root.get("vehicleId"), vehicleId);
    }

    public static Specification<Booking> withCustomerId(UUID customerId) {
        return (root, query, cb) ->
                customerId == null ? null : cb.equal(root.get("customerId"), customerId);
    }
}
