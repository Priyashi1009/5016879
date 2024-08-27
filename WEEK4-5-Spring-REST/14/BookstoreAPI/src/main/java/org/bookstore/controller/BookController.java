package org.bookstore.controller;

import org.bookstore.mapper.BookMapper;
import org.bookstore.dto.BookDTO;
import org.bookstore.entity.Book;
import org.bookstore.exception.CustomException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.bookstore.metrics.CustomMetrics;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/books")
@Validated
public class BookController {
    private final List<Book> books= new ArrayList<>();
    private final BookMapper bookMapper;
    private final CustomMetrics customMetrics;


    public BookController(BookMapper bookMapper, CustomMetrics customMetrics) {
        this.bookMapper = bookMapper;
        this.customMetrics = customMetrics;
    }

    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CollectionModel<BookDTO>> getBooks() {
        if(books.isEmpty())
        {
            throw new CustomException("Empty book list");
        }
        List<BookDTO> bookDTOs = new ArrayList<>();
        for (Book book : books) {
            BookDTO bookDTO = bookMapper.toDTO(book);
            bookDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookController.class).getBookById(bookDTO.getId())).withSelfRel());
            bookDTOs.add(bookDTO);
        }
        CollectionModel<BookDTO> collectionModel = CollectionModel.of(bookDTOs);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Total Books", String.valueOf(bookDTOs.size()));
        return ResponseEntity.ok().headers(headers).body(collectionModel);
    }

    @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<EntityModel<BookDTO>> getBookById(@PathVariable("id") int id) {
        for (Book book : books) {
            if (book.getId() == id){
                BookDTO bookDTO = bookMapper.toDTO(book);
                bookDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookController.class).getBookById(bookDTO.getId())).withSelfRel());
                bookDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookController.class).getBooks()).withRel("books"));
                HttpHeaders headers = new HttpHeaders();
                headers.add("Book Found", "true");
                return ResponseEntity.ok().headers(headers).body(EntityModel.of(bookDTO));
            }
        }
        throw new CustomException("Book with ID " + id + " not found.");
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CollectionModel<BookDTO>> getBookByTitle(
            @RequestParam(value = "title",required = false) String title,
            @RequestParam(value = "author", required = false) String author) {
        List<BookDTO> bookDTOs = new ArrayList<>();
        boolean isTitleEmpty = Objects.isNull(title);
        boolean isAuthorEmpty = Objects.isNull(author);
        for (Book book : books) {
            if(!isTitleEmpty && !isAuthorEmpty) {
                if(book.getTitle().equalsIgnoreCase(title) && book.getAuthor().equalsIgnoreCase(author)) {
                    BookDTO bookDTO = bookMapper.toDTO(book);
                    bookDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookController.class).getBookById(bookDTO.getId())).withSelfRel());
                    bookDTOs.add(bookDTO);
                }
            }
            else{
                if(book.getTitle().equalsIgnoreCase(title)) {
                    BookDTO bookDTO = bookMapper.toDTO(book);
                    bookDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookController.class).getBookById(bookDTO.getId())).withSelfRel());
                    bookDTOs.add(bookDTO);
                }
                else if(book.getAuthor().equalsIgnoreCase(author)) {
                    BookDTO bookDTO = bookMapper.toDTO(book);
                    bookDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookController.class).getBookById(bookDTO.getId())).withSelfRel());
                    bookDTOs.add(bookDTO);
                }
            }
        }
        if(bookDTOs.isEmpty())
            throw new CustomException("No books found for the given criteria.");
        CollectionModel<BookDTO> collectionModel = CollectionModel.of(bookDTOs);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Book Found", "true");
        return ResponseEntity.ok().headers(headers).body(collectionModel);
    }

    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> addBook(@RequestBody BookDTO bookDTO) {
        Book book = bookMapper.toEntity(bookDTO);
        for (Book existingBook : books) {
            if (Objects.equals(existingBook.getId(), book.getId())) {
                throw new CustomException("Book with ID " + book.getId() + " already exists.");
            }
        }
        if (bookDTO==null) {

            throw new CustomException("Empty Body");
        }
        books.add(book);
        customMetrics.incrementBooksAdded();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Book Added", "true");
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body("Book Added Successfully");
    }

    @PutMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> updateBook(@RequestBody BookDTO bookDTO, @PathVariable("id") int id) {
        Book existingBook = books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElseThrow(() -> new CustomException("Book with ID " + id + " not found."));

        if (bookDTO.getTitle() != null)
            existingBook.setTitle(bookDTO.getTitle());
        if (bookDTO.getAuthor() != null)
            existingBook.setAuthor(bookDTO.getAuthor());
        if (bookDTO.getPrice() != null)
            existingBook.setPrice(bookDTO.getPrice());
        if (bookDTO.getIsbn() != null)
            existingBook.setIsbn(bookDTO.getIsbn());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Book Updated", "true");
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body("Book with ID " + id + " updated successfully.");
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<String> deleteBook(@PathVariable("id") int id) {
        int index = 0;
        while (index < books.size()) {
            if (books.get(index).getId() == id) {
                books.remove(index);
                HttpHeaders headers = new HttpHeaders();
                headers.add("Book Deleted", "true");
                return ResponseEntity.status(HttpStatus.ACCEPTED).headers(headers).body("Book with ID " + id + " deleted successfully.");
            }
            index++;
        }
        throw new CustomException("Book with ID " + id + " not found.");
    }
}