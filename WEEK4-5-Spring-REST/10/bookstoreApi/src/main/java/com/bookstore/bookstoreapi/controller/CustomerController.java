package com.bookstore.bookstoreapi.controller;

import com.bookstore.bookstoreapi.dto.CustomerDTO;
import com.bookstore.bookstoreapi.exception.CustomerNotFoundException;
import com.bookstore.bookstoreapi.mapper.CustomerMapper;
import com.bookstore.bookstoreapi.model.Customer;
import com.bookstore.bookstoreapi.repository.CustomerRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerController(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, 
                 produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<CustomerDTO> createCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        Customer customer = customerMapper.toEntity(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "CustomerCreated");
        return new ResponseEntity<>(customerMapper.toDTO(savedCustomer), headers, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with ID: " + id));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "CustomerFetched");
        return new ResponseEntity<>(customerMapper.toDTO(customer), headers, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", 
                consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, 
                produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Long id, @Valid @RequestBody CustomerDTO updatedCustomerDTO) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with ID: " + id));

        customer.setName(updatedCustomerDTO.getName());
        customer.setEmail(updatedCustomerDTO.getEmail());
        customer.setAddress(updatedCustomerDTO.getAddress());

        Customer updatedCustomer = customerRepository.save(customer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "CustomerUpdated");
        return new ResponseEntity<>(customerMapper.toDTO(updatedCustomer), headers, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with ID: " + id));

        customerRepository.delete(customer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "CustomerDeleted");
        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }

    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerDTO> customerDTOs = customers.stream()
                .map(customerMapper::toDTO)
                .toList();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "AllCustomersFetched");
        return new ResponseEntity<>(customerDTOs, headers, HttpStatus.OK);
    }
}
