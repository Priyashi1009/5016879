package com.example.employeemanagementsystem.service;

import com.example.employeemanagementsystem.model.Employee;

import java.util.List;

public interface EmployeeService {
    Employee createEmployee(Employee employee);

    Employee updateEmployee(Employee employee);

    List<Employee> getAllEmployees();

    Employee getEmployeeById(long employeeId);

    void deleteEmployee(long id);
}
