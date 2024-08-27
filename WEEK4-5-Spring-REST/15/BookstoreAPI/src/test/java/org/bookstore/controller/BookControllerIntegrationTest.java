package org.bookstore.controller;

import org.bookstore.dto.BookDTO;
import org.bookstore.entity.Book;
import org.bookstore.mapper.BookMapper;
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

@WebMvcTest(BookController.class)
@ActiveProfiles("test")
public class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookMapper bookMapper;

    @MockBean
    private CustomMetrics customMetrics;

    private List<Book> books;

    @BeforeEach
    void setup() {
        books = new ArrayList<>();
        books.add(new Book(1, "Title1", "Author1", 10.0, 1234567890L, 1));
    }

    @Test
    void testGetBooks() throws Exception {
        List<BookDTO> bookDTOs = new ArrayList<>();
        for (Book book : books) {
            BookDTO dto = new BookDTO(book.getId(), book.getTitle(), book.getAuthor(), book.getPrice(), book.getIsbn());
            bookDTOs.add(dto);
        }

        Mockito.when(bookMapper.toDTO(Mockito.any())).thenAnswer(invocation -> {
            Book book = invocation.getArgument(0);
            return new BookDTO(book.getId(), book.getTitle(), book.getAuthor(), book.getPrice(), book.getIsbn());
        });

        mockMvc.perform(get("/books")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].title").value("Title1"))
                .andExpect(header().string("Total Books", "1"))
                .andDo(MockMvcResultHandlers.print());
    }

    // Add similar tests for other endpoints (e.g., POST, PUT, DELETE)
}