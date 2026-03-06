package com.devjonas.indentity.domain.event;

import com.devjonas.indentity.domain.enums.UserTypes;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserCreatedEvent(
        UUID userId,
        String userEmail,
        UserTypes userTypes,
        LocalDateTime occuredAt
) {
}
