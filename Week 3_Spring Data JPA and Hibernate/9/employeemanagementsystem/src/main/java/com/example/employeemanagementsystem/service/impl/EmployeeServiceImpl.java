package com.example.employeemanagementsystem.service.impl;

import com.example.employeemanagementsystem.exception.ResourceNotFoundException;
import com.example.employeemanagementsystem.model.Employee;
import com.example.employeemanagementsystem.repository.primary.EmployeeRepository;
import com.example.employeemanagementsystem.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        Optional<Employee> employeeDb = this.employeeRepository.findById(employee.getId());

        if (employeeDb.isPresent()) {
            Employee employeeUpdate = employeeDb.get();
            employeeUpdate.setId(employee.getId());
            employeeUpdate.setName(employee.getName());
            employeeUpdate.setEmail(employee.getEmail());
            employeeUpdate.setDepartment(employee.getDepartment());
            employeeRepository.save(employeeUpdate);
            return employeeUpdate;
        } else {
            throw new ResourceNotFoundException("Employee not found with id : " + employee.getId());
        }
    }

    @Override
    public Page<Employee> getAllEmployees(Pageable pageable) {
        return this.employeeRepository.findAll(pageable);
    }

    @Override
    public Employee getEmployeeById(long employeeId) {
        Optional<Employee> employeeDb = this.employeeRepository.findById(employeeId);

        if (employeeDb.isPresent()) {
            return employeeDb.get();
        } else {
            throw new ResourceNotFoundException("Employee not found with id : " + employeeId);
        }
    }

    @Override
    public void deleteEmployee(long employeeId) {
        Optional<Employee> employeeDb = this.employeeRepository.findById(employeeId);

        if (employeeDb.isPresent()) {
            this.employeeRepository.delete(employeeDb.get());
        } else {
            throw new ResourceNotFoundException("Employee not found with id : " + employeeId);
        }
    }
}
