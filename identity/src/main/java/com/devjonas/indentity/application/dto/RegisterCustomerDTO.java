package com.devjonas.indentity.application.dto;

import java.time.LocalDate;

public record RegisterCustomerDTO(
        String name,
        String email,
        String password,
        String cpf,
        String driveLicense,
        String address,
        LocalDate birthDate
) {
}
