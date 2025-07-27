package com.henriq.libraryapi.dto;

import com.henriq.libraryapi.model.BookGender;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record BookResponseDTO(
        UUID id,
        String isbn,
        String title,
        LocalDate publicationDate,
        BookGender gender,
        BigDecimal price,
        AuthorDTO author
) {
}
