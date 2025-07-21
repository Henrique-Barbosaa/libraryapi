package com.henriq.libraryapi.dto;

import com.henriq.libraryapi.model.Author;

import java.time.LocalDate;
import java.util.UUID;

public record AuthorDTO(
        UUID id,
        String name,
        LocalDate birthDate,
        String nationality) {

    public Author createAuthor(){
        Author author = new Author();
        author.setDateOfBirth(this.birthDate);
        author.setName(this.name);
        author.setNationality(this.nationality);

        return author;
    }
}
