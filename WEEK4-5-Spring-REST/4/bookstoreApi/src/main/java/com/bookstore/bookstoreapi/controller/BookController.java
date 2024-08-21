package com.bookstore.bookstoreapi.controller;

import com.bookstore.bookstoreapi.model.Book;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final List<Book> bookList = new ArrayList<>();

    @GetMapping
    public List<Book> getAllBooks() {
        return bookList;
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        return bookList.stream()
                       .filter(book -> book.getId().equals(id))
                       .findFirst()
                       .orElse(null);
    }

    @GetMapping("/search")
    public List<Book> searchBooks(@RequestParam(required = false) String title,
                                  @RequestParam(required = false) String author) {
        return bookList.stream()
                       .filter(book -> (title == null || book.getTitle().equalsIgnoreCase(title)) &&
                                       (author == null || book.getAuthor().equalsIgnoreCase(author)))
                       .toList();
    }

    @PostMapping
    public Book createBook(@RequestBody Book book) {
        bookList.add(book);
        return book;
    }

    @PutMapping("/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {
        Book book = bookList.stream()
                            .filter(b -> b.getId().equals(id))
                            .findFirst()
                            .orElse(null);
        if (book != null) {
            book.setTitle(updatedBook.getTitle());
            book.setAuthor(updatedBook.getAuthor());
            book.setPrice(updatedBook.getPrice());
            book.setIsbn(updatedBook.getIsbn());
        }
        return book;
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookList.removeIf(book -> book.getId().equals(id));
    }
}
