package com.devjonas.inventory.domain.entities;

import com.devjonas.inventory.domain.enums.VehicleStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "vehicles")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
public abstract class Vehicle extends BaseEntity {

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false)
    private VehicleStatus status; // mudar para enum

    @Column(name = "plate", nullable = false, unique = true)
    protected String plate;

    @Column(name = "daily_rate", nullable = false)
    protected Number dailyRate;


    public void reserve() {
        this.setStatus(VehicleStatus.RENTED);
    }
}
