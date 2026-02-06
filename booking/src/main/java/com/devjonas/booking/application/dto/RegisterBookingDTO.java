package com.devjonas.booking.application.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record RegisterBookingDTO(

        @NotNull(message = "Field vehicleId is required")
        UUID vehicleId,

        UUID clientId,

        LocalDateTime startDate,

        LocalDateTime endDate
) {
}
