package com.carService.controller;

import com.carService.model.Appointment;
import com.carService.model.CarService;
import com.carService.model.User;
import com.carService.model.Vehicle;
import com.carService.model.dto.AppointmentRequest;
import com.carService.repository.CarServiceRepository;
import com.carService.repository.InvoiceRepository;
import com.carService.service.AppointmentService;
import com.carService.service.UserService;
import com.carService.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Optional;

@Controller
public class AppointmentController {

    CarServiceRepository carServiceRepository;
    AppointmentService appointmentService;
    UserService userService;
    VehicleService vehicleService;

    @Autowired
    public AppointmentController(CarServiceRepository carServiceRepository, AppointmentService appointmentService,
                                 UserService userService, VehicleService vehicleService) {
        this.carServiceRepository = carServiceRepository;
        this.appointmentService = appointmentService;
        this.userService = userService;
        this.vehicleService = vehicleService;
    }

    @GetMapping("/appointment")
    public String showAppointment(Model model, @RequestParam String id) {

        CarService carService = carServiceRepository.findById(Integer.parseInt(id)).orElse(null);

        model.addAttribute("service",carService);

        return "appointment";
    }

    @PostMapping("/appointment")
    public String makeAppointment(@ModelAttribute AppointmentRequest appointmentRequest, @RequestParam String id) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(appointmentRequest.getDate().isBefore(LocalDate.now())){
            //TODO
        }

        Optional<CarService> carService = carServiceRepository.findById(Integer.parseInt(id));
        if(carService.isPresent()){
            User user = userService.findUserByUsername(principal.getUsername());
            Vehicle vehicle = vehicleService.findByRegistrationNumber(appointmentRequest.getRegistrationNumber()).orElse(null);
            if(vehicle == null){
                vehicle = vehicleService.addVehicle(new Vehicle(appointmentRequest.getBrand(),appointmentRequest.getModel(),appointmentRequest.getRegistrationNumber(),appointmentRequest.getProductionYear()));
            }
            appointmentService.addAppointment(vehicle,user,carService.get(),appointmentRequest.getDate(),appointmentRequest.getHour());
        }

        return "redirect:/services";
    }

    @GetMapping("/myAppointments")
    public String showAppointments(Model model) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findUserByUsername(principal.getUsername());

        model.addAttribute("appointments", appointmentService.findAppointmentsByUser(user));
        return "myAppointments";
    }

    @GetMapping("/cancelAppointment")
    public String cancelAppointment(@RequestParam String id){

        Appointment appointment = appointmentService.findAppointmentById(Integer.parseInt(id)).orElse(null);

        if(appointment!=null) appointmentService.deleteAppointment(appointment);

        return "redirect:/myAppointments";
    }
}
