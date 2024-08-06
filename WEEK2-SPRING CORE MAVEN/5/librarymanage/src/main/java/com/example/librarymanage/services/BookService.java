package com.example.librarymanage.services;

import com.example.librarymanage.repository.BookRepository;

public class BookService {
    private BookRepository bookRepository;

    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void listOfBooks() {
        bookRepository.displayBooks();
    }
}


