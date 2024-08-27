package org.bookstore.controller;

import org.bookstore.dto.CustomerDTO;
import org.bookstore.entity.Customer;
import org.bookstore.mapper.CustomerMapper;
import org.bookstore.metrics.CustomMetrics;
import org.bookstore.exception.CustomException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
@ActiveProfiles("test")
public class CustomerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerMapper customerMapper;

    @MockBean
    private CustomMetrics customMetrics;

    private List<Customer> customers;

    @BeforeEach
    void setup() {
        customers = new ArrayList<>();
        customers.add(new Customer(1, "John Doe", "123 Elm Street", 1234567890L, 1));
    }

    @Test
    void testGetCustomers() throws Exception {
        List<CustomerDTO> customerDTOs = new ArrayList<>();
        for (Customer customer : customers) {
            CustomerDTO dto = new CustomerDTO(customer.getId(), customer.getName(), customer.getAddress(), customer.getPhone());
            customerDTOs.add(dto);
        }

        Mockito.when(customerMapper.toDTO(Mockito.any())).thenAnswer(invocation -> {
            Customer customer = invocation.getArgument(0);
            return new CustomerDTO(customer.getId(), customer.getName(), customer.getAddress(), customer.getPhone());
        });

        mockMvc.perform(get("/customers")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].name").value("John Doe"))
                .andExpect(header().string("Total Customers", "1"))
                .andDo(MockMvcResultHandlers.print());
    }

}