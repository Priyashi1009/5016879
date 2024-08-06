package com.example.librarymanage.repository;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepository {
    public void displayBooks(){
        System.out.println("Feching Book data");
    }
    
}
