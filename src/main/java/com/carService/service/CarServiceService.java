package com.carService.service;

import com.carService.model.CarService;
import com.carService.repository.CarServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CarServiceService {


    CarServiceRepository carServiceRepository;

    @Autowired
    public CarServiceService(CarServiceRepository carServiceRepository) {
        this.carServiceRepository = carServiceRepository;
    }

    @Transactional
    public void addService(CarService carService) {
        carServiceRepository.save(carService);
    }

    @Transactional
    public CarService editCarService(CarService carServiceDTO, int id) {

        CarService carService = carServiceRepository.findById(id).orElse(null);

        if(carService != null){
            if (carServiceDTO.getName() != null && !carServiceDTO.getName().equals(carService.getName())) {
                carService.setName(carServiceDTO.getName());
            }

            if (carServiceDTO.getAddress() != null && !carServiceDTO.getAddress().equals(carService.getAddress())) {
                carService.setAddress(carServiceDTO.getAddress());
            }

            if (carServiceDTO.getBrands() != null && !carServiceDTO.getBrands().equals(carService.getBrands())) {
                carService.setBrands(carServiceDTO.getBrands());
            }

            return carServiceRepository.save(carService);
        }

        return null;

    }

    public Optional<CarService> findById(int id){
        return carServiceRepository.findById(id);
    }

    public void delete(CarService carService) {
        carServiceRepository.delete(carService);
    }

    public List<CarService> findAll() {
        return carServiceRepository.findAll();
    }
}
