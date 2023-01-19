package com.carService.service;

import com.carService.model.Appointment;
import com.carService.model.CarService;
import com.carService.model.User;
import com.carService.model.Vehicle;
import com.carService.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    AppointmentRepository appointmentRepository;

    public List<Integer> getAvailableHours(LocalDate date, CarService carService){
        int startHour = 8;
        int endHour = 18;
        List<Integer> result = new ArrayList<>();
        for (int i = startHour; i <= endHour; i++) {
            if(appointmentRepository.findByCarServiceAndDateAndHour(carService,date,i) == null){
                result.add(i);
            }

        }
        return result;
    }

    public Appointment addAppointment(Vehicle vehicle, User user, CarService carService, LocalDate date, int hour){
        if(appointmentRepository.findByCarServiceAndDateAndHour(carService,date,hour) == null){
            Appointment appointment = new Appointment(vehicle,user,carService,date,hour);
            return appointmentRepository.save(appointment);
        }
        return null;
    }

    public List<Appointment> findAppointmentsByUser(User user)
    {
        return appointmentRepository.findByUser(user);
    }

    public List<Appointment> findAppointmentsByCarService(CarService carService)
    {
        return appointmentRepository.findByCarService(carService);
    }

    public void deleteAppointment(Appointment appointment){
        appointmentRepository.delete(appointment);
    }

    public Optional<Appointment> findAppointmentById(Integer id){
        return appointmentRepository.findById(id);
    }
}
