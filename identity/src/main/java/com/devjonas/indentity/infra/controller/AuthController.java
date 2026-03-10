package com.devjonas.indentity.infra.controller;

import com.devjonas.indentity.application.dto.AuthDTO;
import com.devjonas.indentity.application.usecase.AuthEmployee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    @Autowired
    private AuthEmployee authEmployee;

    @PostMapping
    public ResponseEntity<Object> signInEmployee(@RequestBody AuthDTO request) {
        try {
            return ResponseEntity.ok(authEmployee.execute(request));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
