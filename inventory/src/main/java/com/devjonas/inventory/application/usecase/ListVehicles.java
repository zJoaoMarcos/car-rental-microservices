package com.devjonas.inventory.application.usecase;

import com.devjonas.inventory.domain.entities.Vehicle;
import com.devjonas.inventory.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListVehicles {

    @Autowired
    private VehicleRepository vehicleRepository;

    public List<Vehicle> execute() {
        return vehicleRepository.findAll();
    }
}
