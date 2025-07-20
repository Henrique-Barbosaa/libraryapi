package com.henriq.libraryapi.dto;

import com.henriq.libraryapi.model.Author;

import java.time.LocalDate;

public record AuthorDTO(
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
