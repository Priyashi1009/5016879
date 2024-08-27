package org.bookstore.dto;

import org.springframework.hateoas.RepresentationModel;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO extends RepresentationModel<BookDTO> {
    @NotNull
    private Integer id;

    @NotNull
    @Size(min = 1, max = 100)
    private String title;

    @NotNull
    @Size(min = 1, max = 100)
    private String author;

    @NotNull
    @Min(value = 0, message = "Price should not be less than 0")
    private Double price;

    @NotNull
    private Long isbn;
}
