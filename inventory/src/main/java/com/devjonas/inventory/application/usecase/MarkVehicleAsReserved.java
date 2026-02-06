package com.devjonas.inventory.application.usecase;

import com.devjonas.inventory.domain.entities.Vehicle;
import com.devjonas.inventory.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MarkVehicleAsReserved {

    @Autowired
    private VehicleRepository vehicleRepository;

    public void execute(UUID vehicleId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
        System.out.println("marking as reserved:" + vehicle);
        vehicle.reserve();

        vehicleRepository.save(vehicle);
    }
}
