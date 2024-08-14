package com.example.employeemanagementsystem.service;

import com.example.employeemanagementsystem.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



public interface EmployeeService {
    Employee createEmployee(Employee employee);

    Employee updateEmployee(Employee employee);

    Page<Employee> getAllEmployees(Pageable pageable);

    Employee getEmployeeById(long employeeId);

    void deleteEmployee(long id);
}
