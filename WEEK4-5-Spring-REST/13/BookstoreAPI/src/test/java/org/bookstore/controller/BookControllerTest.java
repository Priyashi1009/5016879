package org.bookstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bookstore.dto.BookDTO;
import org.bookstore.entity.Book;
import org.bookstore.exception.CustomException;
import org.bookstore.mapper.BookMapper;
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

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private BookMapper bookMapper;

    @Mock
    private CustomMetrics customMetrics;

    @InjectMocks
    private BookController bookController;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetBooks() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testGetBookById() throws Exception {
        Book book = new Book(1, "Title", "Author", 20.0, 1234567890L, 1);
        BookDTO bookDTO = new BookDTO(1, "Title", "Author", 20.0, 1234567890L);

        when(bookMapper.toDTO(book)).thenReturn(bookDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/books/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testAddBook() throws Exception {
        BookDTO bookDTO = new BookDTO(1, "Title", "Author", 20.0, 123456789L);

        mockMvc.perform(MockMvcRequestBuilders.post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void testUpdateBook() throws Exception {
        BookDTO bookDTO = new BookDTO(1, "Title Updated", "Author Updated", 25.0, 1234567891L);

        mockMvc.perform(MockMvcRequestBuilders.put("/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteBook() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/books/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

    @Test
    void testExceptionHandling() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/books/99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}