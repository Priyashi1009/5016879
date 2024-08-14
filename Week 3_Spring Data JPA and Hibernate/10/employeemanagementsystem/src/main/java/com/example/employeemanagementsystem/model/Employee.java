package com.example.employeemanagementsystem.model;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NamedQueries({
    @NamedQuery(name = "Employee.findByName", query = "SELECT e FROM Employee e WHERE e.name = :name"),
    @NamedQuery(name = "Employee.findByEmail", query = "SELECT e FROM Employee e WHERE e.email = :email")
})

@Table(name = "employees")
@Data 
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@BatchSize(size = 10)
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "department_id") 
    private Department department;
}
