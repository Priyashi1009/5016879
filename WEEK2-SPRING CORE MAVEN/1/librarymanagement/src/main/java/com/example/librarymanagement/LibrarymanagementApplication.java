package com.example.librarymanagement;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.librarymanagement.services.BookService;

@SpringBootApplication
public class LibrarymanagementApplication {

	public static void main(String[] args) {
		BookService bookService = new BookService();
        bookService.listOfBooks();
	}

}
