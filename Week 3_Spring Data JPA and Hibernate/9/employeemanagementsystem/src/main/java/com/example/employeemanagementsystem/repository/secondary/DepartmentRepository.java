package com.example.employeemanagementsystem.repository.secondary;

import com.example.employeemanagementsystem.dto.secondary.DepartmentDto;
import com.example.employeemanagementsystem.dto.secondary.DepartmentProjection;
import com.example.employeemanagementsystem.model.Department;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    @Query(name = "Department.findByName")
    Department findByName(String name);

    @Query("SELECT new com.example.employeemanagementsystem.dto.secondary.DepartmentDto(d.id, d.name) FROM Department d")
    List<DepartmentDto> findDepartmentDto();

    @Query("SELECT d.name AS DepatmentName, SIZE(d.employees) AS NoOfEmployee FROM Department d")
    List<DepartmentProjection> findDepartmentProjections();
}
