package com.example.employeemanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.example.employeemanagementsystem.config.AuditorAwareImpl;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class EmployeemanagementsystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeemanagementsystemApplication.class, args);
	}

	@Bean
    public AuditorAwareImpl auditorAware() {
        return new AuditorAwareImpl();
    }

}
