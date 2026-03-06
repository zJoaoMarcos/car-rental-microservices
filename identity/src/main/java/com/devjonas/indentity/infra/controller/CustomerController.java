package com.devjonas.indentity.infra.controller;

import com.devjonas.indentity.application.dto.RegisterCustomerDTO;
import com.devjonas.indentity.application.usecase.*;
import com.devjonas.indentity.infra.exception.UserAlreadyExistsException;
import com.devjonas.indentity.infra.exception.UserNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/customers")
public class CustomerController {

    @Autowired
    private GetCustomerById getCustomerById;

    @Autowired
    private ListCustomers listCustomers;

    @Autowired
    private RegisterNewCustomer registerNewCustomer;

    @PostMapping
    public ResponseEntity<Object> register(@Valid @RequestBody RegisterCustomerDTO requestDTO) {
        try {
            return ResponseEntity.ok(registerNewCustomer.execute(requestDTO));
        } catch (UserAlreadyExistsException e ) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<Object> getById(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(getCustomerById.execute(id));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok(listCustomers.execute());
    }
}
