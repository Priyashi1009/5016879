package com.bookstore.bookstoreapi.controller;

import com.bookstore.bookstoreapi.dto.BookDTO;
import com.bookstore.bookstoreapi.exception.BookNotFoundException;
import com.bookstore.bookstoreapi.mapper.BookMapper;
import com.bookstore.bookstoreapi.model.Book;
import com.bookstore.bookstoreapi.repository.BookRepository;
import jakarta.validation.Valid;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(book -> {
                    BookDTO bookDTO = BookMapper.INSTANCE.toDTO(book);
                    Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookController.class).getBookById(book.getId())).withSelfRel();
                    bookDTO.add(selfLink);
                    return bookDTO;
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isEmpty()) {
            throw new BookNotFoundException("Book not found with id " + id);
        }
        BookDTO bookDTO = BookMapper.INSTANCE.toDTO(book.get());
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookController.class).getBookById(id)).withSelfRel();
        bookDTO.add(selfLink);
        return ResponseEntity.ok(bookDTO);
    }

    @PostMapping
    public ResponseEntity<BookDTO> createBook(@Valid @RequestBody BookDTO bookDTO) {
        Book book = BookMapper.INSTANCE.toEntity(bookDTO);
        bookRepository.save(book);

        BookDTO savedBookDTO = BookMapper.INSTANCE.toDTO(book);
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookController.class).getBookById(book.getId())).withSelfRel();
        savedBookDTO.add(selfLink);

        return ResponseEntity.status(201).body(savedBookDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @Valid @RequestBody BookDTO updatedBookDTO) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isEmpty()) {
            throw new BookNotFoundException("Book not found with id " + id);
        }
        Book book = optionalBook.get();
        book.setTitle(updatedBookDTO.getTitle());
        book.setAuthor(updatedBookDTO.getAuthor());
        book.setPrice(updatedBookDTO.getPrice());
        book.setIsbn(updatedBookDTO.getIsbn());
        bookRepository.save(book);

        BookDTO bookDTO = BookMapper.INSTANCE.toDTO(book);
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookController.class).getBookById(id)).withSelfRel();
        bookDTO.add(selfLink);

        return ResponseEntity.ok(bookDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException("Book not found with id " + id);
        }
        bookRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
