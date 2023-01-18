package com.carService.controller;

import com.carService.model.Appointment;
import com.carService.model.CarService;
import com.carService.model.User;
import com.carService.model.Vehicle;
import com.carService.model.dto.AppointmentRequest;
import com.carService.repository.AppointmentRepository;
import com.carService.repository.CarServiceRepository;
import com.carService.service.AppointmentService;
import com.carService.service.UserService;
import com.carService.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class CarServiceController {

    @Autowired
    CarServiceRepository carServiceRepository;
    @Autowired
    AppointmentService appointmentService;
    @Autowired
    UserService userService;
    @Autowired
    VehicleService vehicleService;

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

    @GetMapping("/services")
    public String showServices(Model model) {

        model.addAttribute("services", carServiceRepository.findAll());
        return "carServices";
    }

    @GetMapping("/getAvailableHours")
    public ResponseEntity<List<Integer>> getAvailableHours(@RequestParam("date") LocalDate date, @RequestParam("id") String id){
        CarService carService = carServiceRepository.findById(Integer.parseInt(id)).orElse(null);
        if(carService != null)
            return new ResponseEntity<List<Integer>>(appointmentService.getAvailableHours(date,carService),HttpStatus.OK);
        else
            return new ResponseEntity<List<Integer>>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/cancelAppointment")
    public String cancelAppointment(@RequestParam String id){

        Appointment appointment = appointmentService.findAppointmentById(Integer.parseInt(id)).orElse(null);

        if(appointment!=null) appointmentService.deleteAppointment(appointment);

        return "redirect:/myAppointments";
    }
}
