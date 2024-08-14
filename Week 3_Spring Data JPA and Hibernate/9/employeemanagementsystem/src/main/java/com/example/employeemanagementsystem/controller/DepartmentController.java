package com.example.employeemanagementsystem.controller;

import com.example.employeemanagementsystem.model.Department;
import com.example.employeemanagementsystem.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @GetMapping
    public ResponseEntity<List<Department>> getAllDepartments() {
        return ResponseEntity.ok().body(departmentService.getAllDepartments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable long id) {
        return ResponseEntity.ok().body(departmentService.getDepartmentById(id));
    }

    @PostMapping
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
        return ResponseEntity.ok().body(departmentService.createDepartment(department));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable long id, @RequestBody Department department) {
        department.setId(id);
        return ResponseEntity.ok().body(departmentService.updateDepartment(department));
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteDepartment(@PathVariable long id) {
        departmentService.deleteDepartment(id);
        return HttpStatus.OK;
    }

    @GetMapping("/department-summaries")
    public ResponseEntity<List<DepartmentDto>> getDepartmentSummaries() {
        List<DepartmentDto> summaries = departmentRepository.findDepartmentDto();
        return ResponseEntity.ok(summaries);
    }

    @GetMapping("/department-projections")
    public ResponseEntity<List<DepartmentProjection>> getDepartmentProjections() {
        List<DepartmentProjection> projections = departmentRepository.findDepartmentProjections();
        return ResponseEntity.ok(projections);
    }

}
