package com.carService.controller;

import com.carService.model.*;
import com.carService.repository.CarServiceRepository;
import com.carService.repository.InvoiceRepository;
import com.carService.service.AppointmentService;
import com.carService.service.UserService;
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
import java.util.List;

@Controller
public class EmployeeController {

    @Autowired
    CarServiceRepository carServiceRepository;
    @Autowired
    AppointmentService appointmentService;
    @Autowired
    UserService userService;
    @Autowired
    InvoiceRepository invoiceRepository;

    @GetMapping("/serviceAppointments")
    public String showAppointment(Model model, @RequestParam String id) {

        CarService carService = carServiceRepository.findById(Integer.parseInt(id)).orElse(null);
        List<Appointment> appointments = null;


        if(carService != null){
            appointments = appointmentService.findAppointmentsByCarService(carService);

            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(!principal.toString().equals("anonymousUser")){
                User user = userService.findUserByUsername(((UserDetails)principal).getUsername());
                // if(!carService.getEmployeeList().contains(user)) return "redirect:/services"; TODO
            }
        }

        model.addAttribute("appointments",appointments);
        model.addAttribute("service",carService);

        return "serviceAppointments";
    }

    @GetMapping("/serviceInvoices")
    public String showInvoices(Model model, @RequestParam String id) {

        CarService carService = carServiceRepository.findById(Integer.parseInt(id)).orElse(null);
        List<Invoice> invoices = null;

        if(carService != null){
            invoices = invoiceRepository.findInvoicesByCarService(carService);

            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(!principal.toString().equals("anonymousUser")){
                User user = userService.findUserByUsername(((UserDetails)principal).getUsername());
                // if(!carService.getEmployeeList().contains(user)) return "redirect:/services"; TODO
            }
        }

        model.addAttribute("invoices",invoices);
        //model.addAttribute("service",carService);

        return "serviceInvoices";
    }

    @GetMapping("/createInvoice")
    public String showCreateInvoice(Model model, @RequestParam String id) {

        Appointment appointment = appointmentService.findAppointmentById(Integer.parseInt(id)).orElse(null);

        model.addAttribute("appointment",appointment);

        return "createInvoice";
    }

    @PostMapping("/createInvoice")
    public String createInvoice(@RequestParam String id,@RequestParam("price") String price, @RequestParam("fixedProblem") String fixedProblem) {
        Appointment appointment = appointmentService.findAppointmentById(Integer.parseInt(id)).orElse(null);

        User user = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!principal.toString().equals("anonymousUser")){
            user = userService.findUserByUsername(((UserDetails)principal).getUsername());
        }
        Invoice invoice = null;
        if(appointment != null){
            invoice = new Invoice(appointment.getCarService(), user, appointment.getVehicle(), Double.parseDouble(price), fixedProblem);
            invoiceRepository.save(invoice);
            //TODO if invoice successfully added remove appointment
        }

        return "redirect:/services";
    }
}
