package com.bookstore.bookstoreapi.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;
import com.bookstore.bookstoreapi.repository.BookRepository;

@Component
public class CustomMetrics {

    private final BookRepository bookRepository;

    public CustomMetrics(MeterRegistry meterRegistry, BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        meterRegistry.gauge("bookstore.books.count", bookRepository.findAll().size());
    }
}
