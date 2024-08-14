package com.example.employeemanagementsystem.service.impl;

import com.example.employeemanagementsystem.exception.ResourceNotFoundException;
import com.example.employeemanagementsystem.model.Department;
import com.example.employeemanagementsystem.repository.DepartmentRepository;
import com.example.employeemanagementsystem.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public Department updateDepartment(Department department) {
        Optional<Department> departmentDb = this.departmentRepository.findById(department.getId());

        if (departmentDb.isPresent()) {
            Department departmentUpdate = departmentDb.get();
            departmentUpdate.setId(department.getId());
            departmentUpdate.setName(department.getName());
            departmentRepository.save(departmentUpdate);
            return departmentUpdate;
        } else {
            throw new ResourceNotFoundException("Department not found with id : " + department.getId());
        }
    }

    @Override
    public List<Department> getAllDepartments() {
        return this.departmentRepository.findAll();
    }

    @Override
    public Department getDepartmentById(long departmentId) {
        Optional<Department> departmentDb = this.departmentRepository.findById(departmentId);

        if (departmentDb.isPresent()) {
            return departmentDb.get();
        } else {
            throw new ResourceNotFoundException("Department not found with id : " + departmentId);
        }
    }

    @Override
    public void deleteDepartment(long departmentId) {
        Optional<Department> departmentDb = this.departmentRepository.findById(departmentId);

        if (departmentDb.isPresent()) {
            this.departmentRepository.delete(departmentDb.get());
        } else {
            throw new ResourceNotFoundException("Department not found with id : " + departmentId);
        }
    }
}
