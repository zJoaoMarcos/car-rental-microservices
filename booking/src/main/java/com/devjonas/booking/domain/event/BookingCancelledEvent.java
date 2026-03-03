package com.devjonas.booking.domain.event;

import java.time.LocalDateTime;
import java.util.UUID;

public record BookingCancelledEvent(
        UUID bookingId,
        UUID clientId,
        UUID vehicleId,
        LocalDateTime occurredAt
) {
}
