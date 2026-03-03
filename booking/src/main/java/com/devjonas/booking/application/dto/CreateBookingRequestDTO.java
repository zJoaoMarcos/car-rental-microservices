package com.devjonas.booking.application.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateBookingRequestDTO(

        @NotNull(message = "Field vehicleId is required")
        UUID vehicleId,

        UUID customerId,

        LocalDateTime startDate,

        LocalDateTime endDate
) {
}
