package com.carService.service;

import com.carService.model.Appointment;
import com.carService.model.Vehicle;
import com.carService.repository.AppointmentRepository;
import com.carService.repository.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class VehicleServiceTest {

    @Mock
    VehicleRepository vehicleRepository;

    VehicleService vehicleService;

    @BeforeEach
    void initUseCase() {
        vehicleService = new VehicleService(vehicleRepository);
    }

    @Test
    void findByRegistrationNumber() {
        Vehicle vehicle = new Vehicle("BMW","X5","CB1234CA",2012);

        Mockito.lenient().when(vehicleRepository.findByRegistrationNumber("CB1234CA")).thenReturn(Optional.of(vehicle));

        assertEquals(Optional.of(vehicle), vehicleService.findByRegistrationNumber("CB1234CA"));
    }

    @Test
    void addVehicle() {

        Vehicle vehicle = new Vehicle("BMW","X5","CB1234CA",2012);

        Mockito.lenient().when(vehicleRepository.save(vehicle)).thenReturn(vehicle);

        assertEquals(vehicle, vehicleService.addVehicle(vehicle));
    }
}