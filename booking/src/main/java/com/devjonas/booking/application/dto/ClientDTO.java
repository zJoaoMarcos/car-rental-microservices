package com.devjonas.booking.application.dto;

import java.util.UUID;

public record ClientDTO (
        UUID id,

        String name,

        String email
) {
}
