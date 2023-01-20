package com.carService;

import com.carService.model.Role;
import com.carService.repository.CarServiceRepository;
import com.carService.repository.InvoiceRepository;
import com.carService.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class CarServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(RoleRepository roleRepository, CarServiceRepository carServiceRepository, InvoiceRepository invoiceRepository) {
		return (args) -> {
			if(roleRepository.findByName("ADMIN").isEmpty()) roleRepository.save(new Role("ADMIN"));
			if(roleRepository.findByName("USER").isEmpty()) roleRepository.save(new Role("USER"));
			if(roleRepository.findByName("EMPLOYEE").isEmpty()) roleRepository.save(new Role("EMPLOYEE"));

			/*carServiceRepository.save(new CarService("Mladost"));
			carServiceRepository.save(new CarService("Druzhba"));*/

			/*List<Object[]> list = invoiceRepository.getInvoicesByProductionYear(1);
			for (Object[] obj : list) {
				System.out.println(obj[0].toString() + " - " + obj[1].toString());
			}

			List<Object[]> list1 = invoiceRepository.getInvoicesByBrand(1);
			for (Object[] obj : list1) {
				System.out.println(obj[0].toString() + " - " + obj[1].toString());
			}*/

		};
	}

}
