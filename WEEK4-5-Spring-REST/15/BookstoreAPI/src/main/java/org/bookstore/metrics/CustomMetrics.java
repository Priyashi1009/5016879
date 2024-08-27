package org.bookstore.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class CustomMetrics {

    private final Counter bookAddedCounter;
    private final Counter customerAddedCounter;

    public CustomMetrics(MeterRegistry meterRegistry) {
        this.bookAddedCounter = Counter.builder("bookstore.books.added")
                .description("Number of books added to the bookstore")
                .register(meterRegistry);

        this.customerAddedCounter = Counter.builder("bookstore.customers.added")
                .description("Number of customers added to the bookstore")
                .register(meterRegistry);
    }

    public void incrementBooksAdded() {
        bookAddedCounter.increment();
    }

    public void incrementCustomersAdded() {
        customerAddedCounter.increment();
    }
}
