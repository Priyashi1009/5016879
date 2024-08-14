package com.example.employeemanagementsystem.repository;

import com.example.employeemanagementsystem.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query(name = "Employee.findByName")
    List<Employee> findByName(String name);

    @Query(name = "Employee.findByEmail")
    List<Employee> findByEmail(String email);
}

