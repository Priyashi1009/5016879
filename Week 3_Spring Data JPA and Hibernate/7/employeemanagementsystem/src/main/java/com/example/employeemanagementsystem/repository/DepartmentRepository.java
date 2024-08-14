package com.example.employeemanagementsystem.repository;

import com.example.employeemanagementsystem.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    @Query(name = "Department.findByName")
    Department findByName(String name);
}
