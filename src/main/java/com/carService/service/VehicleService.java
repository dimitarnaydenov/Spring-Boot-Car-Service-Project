package com.carService.service;

import com.carService.model.Vehicle;
import com.carService.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    public Optional<Vehicle> findByRegistrationNumber(String registrationNumber){
        return vehicleRepository.findByRegistrationNumber(registrationNumber);
    }

    public Vehicle addVehicle(Vehicle vehicle){
        return vehicleRepository.save(vehicle);
    }
}