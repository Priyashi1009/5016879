package com.example.employeemanagementsystem.service;

import com.example.employeemanagementsystem.model.Department;

import java.util.List;

public interface DepartmentService {
    Department createDepartment(Department department);

    Department updateDepartment(Department department);

    List<Department> getAllDepartments();

    Department getDepartmentById(long departmentId);

    void deleteDepartment(long id);

    void bulkInsertDepartments(List<Department> departments); 
}
