package com.carService.repository;

import com.carService.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);

    User findByEmail(String email);

    @Query(value = "SELECT car_service.user.id, car_service.user.email, car_service.user.first_name, car_service.user.last_name, \n" +
            "car_service.user.username, car_service.user.password, qualification_id FROM car_service.user \n" +
            "LEFT JOIN user_roles ON user.id=user_id \n" +
            "LEFT JOIN role ON roles_id=role.id \n" +
            "LEFT JOIN car_service_employee_list ON employee_list_id=user.id\n" +
            "where role.name = 'EMPLOYEE' and\n" +
            "employee_list_id is NULL\n" +
            ";",
            nativeQuery = true)
    List<User> findAllUnemployedWorkers();
}
