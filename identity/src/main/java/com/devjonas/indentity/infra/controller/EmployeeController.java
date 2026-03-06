package com.devjonas.indentity.infra.controller;

import com.devjonas.indentity.application.dto.RegisterEmployeeDTO;
import com.devjonas.indentity.application.usecase.GetEmployeeById;
import com.devjonas.indentity.application.usecase.ListEmployees;
import com.devjonas.indentity.application.usecase.RegisterNewEmployee;
import com.devjonas.indentity.infra.exception.UserAlreadyExistsException;
import com.devjonas.indentity.infra.exception.UserNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/employees")
public class EmployeeController {

    @Autowired
    private GetEmployeeById getEmployeeById;

    @Autowired
    private ListEmployees listEmployees;

    @Autowired
    private RegisterNewEmployee registerNewEmployee;

    @PostMapping
    public  ResponseEntity<Object> register(@Valid @RequestBody RegisterEmployeeDTO requestDTO) {
        try {
            return ResponseEntity.ok(registerNewEmployee.execute(requestDTO));
        } catch (UserAlreadyExistsException e ) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<Object> getById(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(getEmployeeById.execute(id));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok(listEmployees.execute());
    }
}
