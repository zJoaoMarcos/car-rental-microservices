package com.devjonas.booking.application.dto;

import java.util.UUID;

public record ListBookingsRequestDTO(UUID customerId, UUID vehicleId) {
}
