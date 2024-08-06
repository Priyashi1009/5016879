package com.example.librarymanagement.services;

import com.example.librarymanagement.repository.BookRepository;

public class BookService {
    private BookRepository bookRepository;

    public BookService() {
        this.bookRepository = new BookRepository();
    }

    public void listOfBooks() {
        bookRepository.displayBooks();
    }
}