package com.carService.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.BatchSize;

import java.time.LocalDate;

@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private Vehicle vehicle;

    @ManyToOne
    private User user;

    @ManyToOne
    private CarService carService;

    private LocalDate date;

    @Size(min = 8, max = 18)
    private int hour;

    public Appointment() {
    }

    public Appointment(Vehicle vehicle, User user, CarService carService, LocalDate date, @Size(min = 8, max = 18) int hour) {
        this.vehicle = vehicle;
        this.user = user;
        this.carService = carService;
        this.date = date;
        this.hour = hour;
    }
}
