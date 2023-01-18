package com.carService.model;

import jakarta.persistence.*;

@Entity
public class Mechanic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstName;

    private String lastName;

    private String email;

    @Column(nullable = false, unique = true)
    private String username;

    private String password;

    @ManyToOne
    private CarService carService;
}
