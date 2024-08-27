package org.bookstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bookstore.dto.CustomerDTO;
import org.bookstore.entity.Customer;
import org.bookstore.exception.CustomException;
import org.bookstore.mapper.CustomerMapper;
import org.bookstore.metrics.CustomMetrics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CustomerMapper customerMapper;

    @Mock
    private CustomMetrics customMetrics;

    @InjectMocks
    private CustomerController customerController;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCustomers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/customers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testGetCustomerById() throws Exception {
        Customer customer = new Customer(1, "Name", "Address", 1234567890L, 1);
        CustomerDTO customerDTO = new CustomerDTO(1, "Name", "Address", 1234567890L);

        when(customerMapper.toDTO(customer)).thenReturn(customerDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/customers/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testAddCustomer() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO(1, "Name", "Address", 1234567890L);

        mockMvc.perform(MockMvcRequestBuilders.post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void testUpdateCustomer() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO(1, "Name Updated", "Address Updated", 9876543210L);

        mockMvc.perform(MockMvcRequestBuilders.put("/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteCustomer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/customers/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

    @Test
    void testExceptionHandling() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/customers/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}