package com.devjonas.booking.domain.event;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record BookingCreatedEvent(
        UUID bookingId,
        UUID clientId,
        UUID vehicleId,
        LocalDateTime occurredAt
) {
}
