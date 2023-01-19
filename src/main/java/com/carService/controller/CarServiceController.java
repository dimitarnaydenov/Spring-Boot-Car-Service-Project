package com.carService.controller;

import com.carService.model.Appointment;
import com.carService.model.CarService;
import com.carService.model.User;
import com.carService.model.Vehicle;
import com.carService.model.dto.AppointmentRequest;
import com.carService.repository.AppointmentRepository;
import com.carService.repository.CarServiceRepository;
import com.carService.repository.InvoiceRepository;
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
    @Autowired
    InvoiceRepository invoiceRepository;

    @GetMapping("/addService")
    public String showAddService() {

        return "addService";
    }

    @GetMapping("/editService")
    public String showEditService() {

        return "editService";
    }

    @GetMapping("/deleteService")
    public String deleteService(@RequestParam String id){

         CarService carService = carServiceRepository.findById(Integer.parseInt(id)).orElse(null);

        if(carService!=null) carServiceRepository.delete(carService);

        return "redirect:/services";
    }

    @GetMapping("/service")
    public String shoService(Model model, @RequestParam String id) {

        CarService carService = carServiceRepository.findById(Integer.parseInt(id)).orElse(null);

        boolean employeeInService = false;

        if(carService != null){
            model.addAttribute("service",carService);

            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(!principal.toString().equals("anonymousUser")){
                User user = userService.findUserByUsername(((UserDetails)principal).getUsername());
                employeeInService = carService.getEmployeeList().contains(user);
            }
        }

        model.addAttribute("employeeInService",employeeInService);

        return "servicePage";
    }

    @GetMapping("/services")
    public String showServices(Model model) {

        model.addAttribute("services", carServiceRepository.findAll());
        return "carServices";
    }

    @GetMapping("/myInvoices")
    public String showInvoices(Model model) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findUserByUsername(principal.getUsername());

        model.addAttribute("invoices", invoiceRepository.findInvoicesByClient(user));
        return "myInvoices";
    }

    @GetMapping("/getAvailableHours")
    public ResponseEntity<List<Integer>> getAvailableHours(@RequestParam("date") LocalDate date, @RequestParam("id") String id){
        CarService carService = carServiceRepository.findById(Integer.parseInt(id)).orElse(null);
        if(carService != null)
            return new ResponseEntity<List<Integer>>(appointmentService.getAvailableHours(date,carService),HttpStatus.OK);
        else
            return new ResponseEntity<List<Integer>>(HttpStatus.NOT_FOUND);
    }


}
