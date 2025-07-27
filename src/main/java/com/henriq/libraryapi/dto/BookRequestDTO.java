package com.henriq.libraryapi.dto;

import com.henriq.libraryapi.model.BookGender;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record BookRequestDTO(
        String isbn,
        String title,
        LocalDate publicationDate,
        BookGender gender,
        BigDecimal price,
        UUID id_author
) {
}
