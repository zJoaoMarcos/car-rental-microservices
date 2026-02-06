package com.devjonas.booking.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record VehicleDTO(UUID id, BigDecimal dailyRate, String status) {

}
