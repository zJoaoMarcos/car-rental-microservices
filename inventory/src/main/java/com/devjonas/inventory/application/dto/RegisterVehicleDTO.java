package com.devjonas.inventory.application.dto;

import com.devjonas.inventory.domain.enums.VehicleStatus;
import com.devjonas.inventory.domain.enums.VehicleTypes;
import jakarta.validation.constraints.NotNull;

public record RegisterVehicleDTO(

        @NotNull(message = "field Type is required!")
        VehicleTypes type,

        String brand,

        String model,

        String color,

        int year,

        String plate,

        int dailyRate,

        VehicleStatus status
) {
}
