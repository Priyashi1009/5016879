package com.example.employeemanagementsystem.repository;

import com.example.employeemanagementsystem.dto.EmployeeDto;
import com.example.employeemanagementsystem.dto.EmployeeProjection;
import com.example.employeemanagementsystem.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query(name = "Employee.findByName")
    List<Employee> findByName(String name);

    @Query(name = "Employee.findByEmail")
    List<Employee> findByEmail(String email);

    @Query("SELECT new com.example.employeemanagementsystem.dto.EmployeeSummary(e.name, e.email, d.name) " +
            "FROM Employee e JOIN e.department d")
    List<EmployeeDto> findEmployeeDto();

    @Query("SELECT e.name AS Employeename, e.email AS email, d.name AS DepartmentName " +
            "FROM Employee e JOIN e.department d")
    List<EmployeeProjection> findEmployeeProjections();


}

