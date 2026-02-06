package com.devjonas.inventory.application.usecase;

import com.devjonas.inventory.domain.entities.Vehicle;
import com.devjonas.inventory.application.dto.RegisterVehicleDTO;
import com.devjonas.inventory.domain.exception.FailureRegisterVehicleException;
import com.devjonas.inventory.domain.exception.PlateAlreadyExistsException;
import com.devjonas.inventory.domain.factory.FactoryVehicle;
import com.devjonas.inventory.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterNewVehicle {

    @Autowired
    private VehicleRepository vehicleRepository;

    public Vehicle execute(RegisterVehicleDTO request) {

        var plateExists = vehicleRepository.findByPlate(request.plate());

        if (plateExists.isPresent()) {
            throw new PlateAlreadyExistsException();
        }

        FactoryVehicle factory = new FactoryVehicle();
        Vehicle vehicle = factory.getVehicle(request);

        if (vehicle == null) {
            throw new FailureRegisterVehicleException();
        }

        return vehicleRepository.save(vehicle);
    }

}
