package com.bookstore.bookstoreapi.controller;

import com.bookstore.bookstoreapi.dto.BookDTO;
import com.bookstore.bookstoreapi.model.Book;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final List<Book> bookList = new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<BookDTO> bookDTOs = new ArrayList<>();
        for (Book book : bookList) {
            bookDTOs.add(mapToDTO(book));
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "AllBooksFetched");
        return new ResponseEntity<>(bookDTOs, headers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        Book book = bookList.stream()
                            .filter(b -> b.getId().equals(id))
                            .findFirst()
                            .orElse(null);
        if (book == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "BookFetched");
        return new ResponseEntity<>(mapToDTO(book), headers, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookDTO>> searchBooks(@RequestParam(required = false) String title,
                                                     @RequestParam(required = false) String author) {
        List<BookDTO> result = new ArrayList<>();
        for (Book book : bookList) {
            if ((title == null || book.getTitle().equalsIgnoreCase(title)) &&
                (author == null || book.getAuthor().equalsIgnoreCase(author))) {
                result.add(mapToDTO(book));
            }
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "BooksSearched");
        return new ResponseEntity<>(result, headers, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO bookDTO) {
        Book book = mapToEntity(bookDTO);
        bookList.add(book);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "BookCreated");
        return new ResponseEntity<>(mapToDTO(book), headers, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @RequestBody BookDTO updatedBookDTO) {
        Book book = bookList.stream()
                            .filter(b -> b.getId().equals(id))
                            .findFirst()
                            .orElse(null);
        if (book == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        book.setTitle(updatedBookDTO.getTitle());
        book.setAuthor(updatedBookDTO.getAuthor());
        book.setPrice(updatedBookDTO.getPrice());
        book.setIsbn(updatedBookDTO.getIsbn());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "BookUpdated");
        return new ResponseEntity<>(mapToDTO(book), headers, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        boolean removed = bookList.removeIf(book -> book.getId().equals(id));
        if (!removed) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "BookDeleted");
        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }

    private BookDTO mapToDTO(Book book) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setAuthor(book.getAuthor());
        bookDTO.setPrice(book.getPrice());
        bookDTO.setIsbn(book.getIsbn());
        return bookDTO;
    }

    private Book mapToEntity(BookDTO bookDTO) {
        Book book = new Book();
        book.setId(bookDTO.getId());
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setPrice(bookDTO.getPrice());
        book.setIsbn(bookDTO.getIsbn());
        return book;
    }
}
