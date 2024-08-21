package com.bookstore.bookstoreapi.controller;

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
    public ResponseEntity<List<Book>> getAllBooks() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "AllBooksFetched");
        return new ResponseEntity<>(bookList, headers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book book = bookList.stream()
                            .filter(b -> b.getId().equals(id))
                            .findFirst()
                            .orElse(null);
        if (book == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "BookFetched");
        return new ResponseEntity<>(book, headers, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(@RequestParam(required = false) String title,
                                                  @RequestParam(required = false) String author) {
        List<Book> result = bookList.stream()
                .filter(book -> (title == null || book.getTitle().equalsIgnoreCase(title)) &&
                                (author == null || book.getAuthor().equalsIgnoreCase(author)))
                .toList();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "BooksSearched");
        return new ResponseEntity<>(result, headers, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        bookList.add(book);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "BookCreated");
        return new ResponseEntity<>(book, headers, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {
        Book book = bookList.stream()
                            .filter(b -> b.getId().equals(id))
                            .findFirst()
                            .orElse(null);
        if (book == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        book.setTitle(updatedBook.getTitle());
        book.setAuthor(updatedBook.getAuthor());
        book.setPrice(updatedBook.getPrice());
        book.setIsbn(updatedBook.getIsbn());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "BookUpdated");
        return new ResponseEntity<>(book, headers, HttpStatus.OK);
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
}
