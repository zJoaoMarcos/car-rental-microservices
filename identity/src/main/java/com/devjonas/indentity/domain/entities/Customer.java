package com.devjonas.indentity.domain.entities;

import com.devjonas.indentity.domain.enums.CustomerStatus;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("CUSTOMER")
@Getter
@Setter
public class Customer extends User {

    @Column(name = "birth_date")
    private LocalDate birthDate;

    private String address;

    private String driveLicense;

    private CustomerStatus status;
}
