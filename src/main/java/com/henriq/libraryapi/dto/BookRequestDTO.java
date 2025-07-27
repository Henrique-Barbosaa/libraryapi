package com.henriq.libraryapi.dto;

import com.henriq.libraryapi.model.Book;
import com.henriq.libraryapi.model.BookGender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.ISBN;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record BookRequestDTO(
        @NotBlank(message = "Campo obrigatório")
        @ISBN(message = "ISBN inválido")
        String isbn,
        @NotBlank(message = "Campo obrigatório")
        String title,
        @NotNull(message = "Campo obrigatório")
        @Past(message = "Datas futuras não são permitidas nesse campo")
        LocalDate publicationDate,
        BookGender gender,
        BigDecimal price,
        @NotNull(message = "Campo obrigatório")
        UUID id_author
) {
}
