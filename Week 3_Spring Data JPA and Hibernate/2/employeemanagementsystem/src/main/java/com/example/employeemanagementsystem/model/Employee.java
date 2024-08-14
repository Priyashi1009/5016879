package com.example.employeemanagementsystem.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "employees")
@Data // This generates getters, setters, toString, equals, and hashcode methods.
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    @ManyToOne
    @JoinColumn(name = "department_id") // Foreign key column
    private Department department;
}
