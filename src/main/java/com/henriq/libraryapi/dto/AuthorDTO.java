package com.henriq.libraryapi.dto;

import com.henriq.libraryapi.model.Author;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record AuthorDTO(
        UUID id,
        @NotBlank(message = "Campo obrigatório")
        String name,
        @NotNull(message = "Campo obrigatório")
        LocalDate birthDate,
        @NotBlank(message = "Campo obrigatório")
        String nationality) {

    public Author createAuthor(){
        Author author = new Author();
        author.setDateOfBirth(this.birthDate);
        author.setName(this.name);
        author.setNationality(this.nationality);

        return author;
    }
}
