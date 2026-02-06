package com.devjonas.inventory.domain.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("SUV")
@Getter
@Setter
public class SuvCar extends Vehicle {

}
