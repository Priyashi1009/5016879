package com.example.employeemanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeDto {
    private String name;
    private String email;
    private String departmentName;
}