package com.carService.model;

import jakarta.persistence.*;
import org.hibernate.annotations.BatchSize;

@Entity
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private String  registrationNumber;

    @Column(nullable = false)
    private Integer productionYear;

    public Vehicle() {
    }

    public Vehicle(String brand, String model, String registrationNumber, Integer productionYear) {
        this.brand = brand;
        this.model = model;
        this.registrationNumber = registrationNumber;
        this.productionYear = productionYear;
    }
}
