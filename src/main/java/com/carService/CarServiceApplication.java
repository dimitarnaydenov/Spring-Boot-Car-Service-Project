package com.carService;

import com.carService.model.CarService;
import com.carService.model.Role;
import com.carService.repository.CarServiceRepository;
import com.carService.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CarServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(RoleRepository roleRepository, CarServiceRepository carServiceRepository) {
		return (args) -> {
			/*roleRepository.save(new Role("Customer"));
			roleRepository.save(new Role("Employee"));
			roleRepository.save(new Role("Service"));*/

			/*carServiceRepository.save(new CarService("Mladost"));
			carServiceRepository.save(new CarService("Druzhba"));*/
		};
	}

}
