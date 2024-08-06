package com.example.librarymanagement.services;

import com.example.librarymanagement.repository.BookRepository;

public class BookService {
    private BookRepository bookRepository;

    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void listOfBooks() {
        bookRepository.displayBooks();
    }
}


