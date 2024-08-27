package org.bookstore.controller;

import org.bookstore.dto.CustomerDTO;
import org.bookstore.entity.Customer;
import org.bookstore.exception.CustomException;
import org.bookstore.mapper.CustomerMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.bookstore.metrics.CustomMetrics;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/customers")
@Validated
public class CustomerController {

    private final List<Customer> customers = new ArrayList<>();
    private final CustomerMapper customerMapper;
    private final CustomMetrics customMetrics;

    public CustomerController(CustomerMapper customerMapper, CustomMetrics customMetrics) {
        this.customerMapper = customerMapper;
        this.customMetrics = customMetrics;
    }

    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> addCustomer(@RequestBody CustomerDTO customerDTO) {
        Customer customer = customerMapper.toEntity(customerDTO);
        for (Customer existingCustomer : customers) {
            if (Objects.equals(existingCustomer.getId(), customer.getId())) {
                throw new CustomException("Customer with ID " + customer.getId() + " already exists.");
            }
        }
        customers.add(customer);
        customMetrics.incrementCustomersAdded();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Customer Added", "true");

        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body("Customer with ID " + customer.getId() + " added successfully.");
    }

    @PostMapping(value = "/registrations", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> addCustomerWithParams(
            @RequestParam(value = "id") int id,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "address") String address,
            @RequestParam(value = "phone") long phone
    ) {
        CustomerDTO customerDTO = new CustomerDTO(id, name, address, phone);
        Customer customer = customerMapper.toEntity(customerDTO);
        customers.add(customer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Customer Added", "true");

        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body("Customer with ID " + customer.getId() + " added successfully.");
    }

    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CollectionModel<CustomerDTO>> getCustomers() {
        if (customers.isEmpty()) {
            throw new CustomException("Empty customers list");
        }
        List<CustomerDTO> customerDTOs = new ArrayList<>();
        for (Customer customer : customers) {
            CustomerDTO customerDTO = customerMapper.toDTO(customer);
            customerDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CustomerController.class).getCustomerById(customerDTO.getId())).withSelfRel());
            customerDTOs.add(customerDTO);
        }
        CollectionModel<CustomerDTO> collectionModel = CollectionModel.of(customerDTOs);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Total Customers", String.valueOf(customerDTOs.size()));
        return ResponseEntity.ok().headers(headers).body(collectionModel);
    }

    @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<EntityModel<CustomerDTO>> getCustomerById(@PathVariable("id") int id) {
        for (Customer customer : customers) {
            if (customer.getId() == id) {
                CustomerDTO customerDTO = customerMapper.toDTO(customer);
                customerDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CustomerController.class).getCustomerById(customerDTO.getId())).withSelfRel());
                customerDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CustomerController.class).getCustomers()).withRel("customers"));
                HttpHeaders headers = new HttpHeaders();
                headers.add("Customer Found", "true");
                return ResponseEntity.ok().headers(headers).body(EntityModel.of(customerDTO));
            }
        }
        throw new CustomException("Customer with ID " + id + " not found.");
    }

    @PutMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> updateCustomer(@RequestBody CustomerDTO customerDTO, @PathVariable("id") int id) {
        Customer existingCustomer = customers.stream()
                .filter(customer -> customer.getId() == id)
                .findFirst()
                .orElseThrow(() -> new CustomException("Customer with ID " + id + " not found."));

        if (customerDTO.getName() != null)
            existingCustomer.setName(customerDTO.getName());
        if (customerDTO.getAddress() != null)
            existingCustomer.setAddress(customerDTO.getAddress());
        if (customerDTO.getPhone() != 0)
            existingCustomer.setPhone(customerDTO.getPhone());  // Note: This assumes phone number can't be zero

        HttpHeaders headers = new HttpHeaders();
        headers.add("Customer Updated", "true");
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body("Customer with ID " + id + " updated successfully.");
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<String> deleteCustomer(@PathVariable("id") int id) {
        boolean removed = customers.removeIf(customer -> customer.getId() == id);
        if (removed) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Customer Deleted", "true");
            return ResponseEntity.status(HttpStatus.ACCEPTED).headers(headers).body("Customer with ID " + id + " deleted successfully.");
        }
        throw new CustomException("Customer with ID " + id + " not found.");
    }
}