package com.devjonas.inventory.domain.entities;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("ECONOMIC")
@Getter
@Setter
public class EconomicCar extends Vehicle {

}
