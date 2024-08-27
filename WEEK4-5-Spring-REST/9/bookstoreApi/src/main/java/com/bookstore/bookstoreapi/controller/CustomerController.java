package com.bookstore.bookstoreapi.controller;

import com.bookstore.bookstoreapi.dto.CustomerDTO;
import com.bookstore.bookstoreapi.exception.CustomerNotFoundException;
import com.bookstore.bookstoreapi.mapper.CustomerMapper;
import com.bookstore.bookstoreapi.model.Customer;
import com.bookstore.bookstoreapi.repository.CustomerRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper customerMapper;

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        Customer customer = customerMapper.toEntity(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);

        CustomerDTO savedCustomerDTO = customerMapper.toDTO(savedCustomer);
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CustomerController.class).getCustomerById(savedCustomer.getId())).withSelfRel();
        savedCustomerDTO.add(selfLink);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "CustomerCreated");
        return new ResponseEntity<>(savedCustomerDTO, headers, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with ID: " + id));

        CustomerDTO customerDTO = customerMapper.toDTO(customer);
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CustomerController.class).getCustomerById(id)).withSelfRel();
        customerDTO.add(selfLink);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "CustomerFetched");
        return new ResponseEntity<>(customerDTO, headers, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Long id, @Valid @RequestBody CustomerDTO updatedCustomerDTO) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with ID: " + id));

        customer.setName(updatedCustomerDTO.getName());
        customer.setEmail(updatedCustomerDTO.getEmail());
        customer.setAddress(updatedCustomerDTO.getAddress());

        Customer updatedCustomer = customerRepository.save(customer);

        CustomerDTO customerDTO = customerMapper.toDTO(updatedCustomer);
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CustomerController.class).getCustomerById(id)).withSelfRel();
        customerDTO.add(selfLink);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "CustomerUpdated");
        return new ResponseEntity<>(customerDTO, headers, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with ID: " + id));

        customerRepository.delete(customer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "CustomerDeleted");
        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerDTO> customerDTOs = customers.stream()
                .map(customer -> {
                    CustomerDTO customerDTO = customerMapper.toDTO(customer);
                    Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CustomerController.class).getCustomerById(customer.getId())).withSelfRel();
                    customerDTO.add(selfLink);
                    return customerDTO;
                })
                .collect(Collectors.toList());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "AllCustomersFetched");
        return new ResponseEntity<>(customerDTOs, headers, HttpStatus.OK);
    }
}
