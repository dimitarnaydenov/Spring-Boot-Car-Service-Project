package com.carService.repository;

import com.carService.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment,Integer> {

    Appointment findByDateAndHour(LocalDate date, int hour);
}
