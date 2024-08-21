package com.bookstore.bookstoreapi.controller;

import com.bookstore.bookstoreapi.dto.CustomerDTO;
import com.bookstore.bookstoreapi.model.Customer;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final List<Customer> customerList = new ArrayList<>();

    @PostMapping("/create")
    public CustomerDTO createCustomer(@RequestBody CustomerDTO customerDTO) {
        Customer customer = mapToEntity(customerDTO);
        customerList.add(customer);
        return mapToDTO(customer);
    }

    @PostMapping("/register")
    public CustomerDTO registerCustomer(@RequestParam String name,
                                        @RequestParam String email,
                                        @RequestParam String address) {
        Customer customer = new Customer();
        customer.setId((long) (customerList.size() + 1));
        customer.setName(name);
        customer.setEmail(email);
        customer.setAddress(address);
        customerList.add(customer);
        return mapToDTO(customer);
    }

    private CustomerDTO mapToDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setAddress(customer.getAddress());
        return customerDTO;
    }

    private Customer mapToEntity(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setId(customerDTO.getId());
        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        customer.setAddress(customerDTO.getAddress());
        return customer;
    }
}
