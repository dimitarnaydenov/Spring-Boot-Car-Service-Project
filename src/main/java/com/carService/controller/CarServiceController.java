package com.carService.controller;

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

        CarService carService = carServiceRepository.findById(Integer.parseInt(id)).get();

        model.addAttribute("service",carService);

        return "appointment";
    }

    @PostMapping("/appointment")
    public String makeAppointment(@ModelAttribute AppointmentRequest appointmentRequest, @RequestParam String id) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<CarService> carService = carServiceRepository.findById(Integer.parseInt(id));
        if(carService.isPresent()){
            User user = userService.findUserByUsername(principal.getUsername());
            Vehicle vehicle = vehicleService.findByRegistrationNumber(appointmentRequest.getRegistrationNumber());
            if(vehicle == null){
                vehicle = vehicleService.addVehicle(new Vehicle());
            }
            appointmentService.addAppointment(vehicle,user,carService.get(),appointmentRequest.getDate(),appointmentRequest.getHour());
        }

        return "redirect:/services";
    }

    @GetMapping("/services")
    public String showServices(Model model) {

        model.addAttribute("services", carServiceRepository.findAll());
        return "carServices";
    }

    @GetMapping("/getAvailableHours")
    public ResponseEntity<List<Integer>> getAvailableHours(@RequestParam LocalDate date){

        return new ResponseEntity<List<Integer>>(appointmentService.getAvailableHours(date),HttpStatus.OK);
    }
}
