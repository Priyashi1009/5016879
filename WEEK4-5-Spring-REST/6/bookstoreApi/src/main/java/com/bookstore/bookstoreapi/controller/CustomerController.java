package com.bookstore.bookstoreapi.controller;

import com.bookstore.bookstoreapi.model.Customer;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final List<Customer> customerList = new ArrayList<>();

    @PostMapping("/create")
    public Customer createCustomer(@RequestBody Customer customer) {
        customerList.add(customer);
        return customer;
    }

    @PostMapping("/register")
    public Customer registerCustomer(@RequestParam String name,
                                     @RequestParam String email,
                                     @RequestParam String address) {
        Customer customer = new Customer();
        customer.setId((long) (customerList.size() + 1));
        customer.setName(name);
        customer.setEmail(email);
        customer.setAddress(address);
        customerList.add(customer);
        return customer;
    }
}
