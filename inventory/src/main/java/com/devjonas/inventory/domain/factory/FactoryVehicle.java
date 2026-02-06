package com.devjonas.inventory.domain.factory;

import com.devjonas.inventory.application.dto.RegisterVehicleDTO;
import com.devjonas.inventory.domain.entities.EconomicCar;
import com.devjonas.inventory.domain.entities.SuvCar;
import com.devjonas.inventory.domain.entities.Vehicle;
import com.devjonas.inventory.domain.enums.VehicleTypes;

public class FactoryVehicle {
    public Vehicle getVehicle(RegisterVehicleDTO dto) {
        Vehicle vehicle = null;
        var type = dto.type();

        if (type == VehicleTypes.SUV) {
            vehicle = new SuvCar();
            vehicle.setBrand(dto.brand());
            vehicle.setModel(dto.model());
            vehicle.setColor(dto.color());
            vehicle.setPlate(dto.plate());
            vehicle.setYear(dto.year());
            vehicle.setDailyRate(dto.dailyRate());
            vehicle.setStatus(dto.status());

            return vehicle;
        }

        if (type == VehicleTypes.ECONOMIC) {
            vehicle = new EconomicCar();
            vehicle.setBrand(dto.brand());
            vehicle.setModel(dto.model());
            vehicle.setColor(dto.color());
            vehicle.setPlate(dto.plate());
            vehicle.setYear(dto.year());
            vehicle.setDailyRate(dto.dailyRate());
            vehicle.setStatus(dto.status());

            return vehicle;
        }

        return vehicle;
    }
}
