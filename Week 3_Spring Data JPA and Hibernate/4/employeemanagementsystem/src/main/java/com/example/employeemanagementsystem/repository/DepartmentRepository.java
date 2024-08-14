package com.example.employeemanagementsystem.repository;

import com.example.employeemanagementsystem.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    // This is the derived query method used to find a department by name
    Department findByName(String name);
}
