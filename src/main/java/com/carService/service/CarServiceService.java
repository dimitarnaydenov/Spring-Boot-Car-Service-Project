package com.carService.service;

import com.carService.model.CarService;
import com.carService.repository.CarServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarServiceService {

    @Autowired
    CarServiceRepository carServiceRepository;


}
