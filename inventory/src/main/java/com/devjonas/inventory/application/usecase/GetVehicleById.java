package com.devjonas.inventory.application.usecase;

import com.devjonas.inventory.domain.entities.Vehicle;
import com.devjonas.inventory.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetVehicleById {

    @Autowired
    private VehicleRepository vehicleRepository;

    public Vehicle execute(UUID id) {
        return vehicleRepository.getReferenceById(id);
    }
}
