package com.devjonas.booking.application.client;

import com.devjonas.booking.application.dto.VehicleDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

import java.util.UUID;

public interface VehicleServiceClient {

    @GetExchange("/v1/vehicles/details/{id}")
    VehicleDTO getVehicleById(@PathVariable UUID id);
}
