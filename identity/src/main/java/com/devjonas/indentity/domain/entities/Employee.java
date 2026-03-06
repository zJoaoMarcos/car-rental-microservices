package com.devjonas.indentity.domain.entities;

import com.devjonas.indentity.domain.enums.EmployeePosition;
import com.devjonas.indentity.domain.enums.EmployeeStatus;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("EMPLOYEE")
@Getter
@Setter
public class Employee extends User {

    private EmployeePosition position;

    @Column(name = "hire_date")
    private LocalDate hireDate;

    private EmployeeStatus status;
}
