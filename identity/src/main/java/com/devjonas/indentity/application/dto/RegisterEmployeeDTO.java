package com.devjonas.indentity.application.dto;

import com.devjonas.indentity.domain.enums.EmployeePosition;

public record RegisterEmployeeDTO(
        String name,
        String email,
        String cpf,
        String password,
        EmployeePosition position
) {
}
