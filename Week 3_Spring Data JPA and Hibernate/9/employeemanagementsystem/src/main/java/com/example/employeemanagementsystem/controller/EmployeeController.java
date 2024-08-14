package com.example.employeemanagementsystem.controller;

import com.example.employeemanagementsystem.model.Employee;
import com.example.employeemanagementsystem.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @GetMapping("/paged")
    public ResponseEntity<Page<Employee>> getAllEmployeesPaginated(Pageable pageable) {
        return ResponseEntity.ok().body(employeeService.getAllEmployees(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable long id) {
        return ResponseEntity.ok().body(employeeService.getEmployeeById(id));
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        return ResponseEntity.ok().body(employeeService.createEmployee(employee));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable long id, @RequestBody Employee employee) {
        employee.setId(id);
        return ResponseEntity.ok().body(employeeService.updateEmployee(employee));
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteEmployee(@PathVariable long id) {
        employeeService.deleteEmployee(id);
        return HttpStatus.OK;
    }
    @GetMapping("/employee-summaries")
    public ResponseEntity<List<EmployeeDto>> getEmployeeSummaries() {
        List<EmployeeDto> summaries = employeeRepository.findEmployeeDto();
        return ResponseEntity.ok(summaries);
    }

    @GetMapping("/employee-projections")
    public ResponseEntity<List<EmployeeProjection>> getEmployeeProjections() {
        List<EmployeeProjection> projections = employeeRepository.findEmployeeProjections();
        return ResponseEntity.ok(projections);
    }
}
