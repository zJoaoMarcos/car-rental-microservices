package com.devjonas.inventory.infra.controller;

import com.devjonas.inventory.application.usecase.GetVehicleById;
import com.devjonas.inventory.application.usecase.ListVehicles;
import com.devjonas.inventory.application.usecase.RegisterNewVehicle;
import com.devjonas.inventory.application.dto.RegisterVehicleDTO;
import com.devjonas.inventory.domain.exception.FailureRegisterVehicleException;
import com.devjonas.inventory.domain.exception.PlateAlreadyExistsException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/vehicles")
public class VehicleController {

    @Autowired
    private RegisterNewVehicle registerNewVehicle;

    @Autowired
    private ListVehicles listVehicles;

    @Autowired
    private GetVehicleById getVehicleById;

    @PostMapping
    public ResponseEntity<Object> register(@Valid @RequestBody RegisterVehicleDTO requestDTO) {
        try {
            return ResponseEntity.ok(registerNewVehicle.execute(requestDTO));
        } catch (PlateAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (FailureRegisterVehicleException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Object> getAll() {
        try {
            return ResponseEntity.ok(listVehicles.execute());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error in process request");
        }
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<Object> getById(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(getVehicleById.execute(id));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error so");
        }
    }
}
