package com.henriq.libraryapi.dto;

import com.henriq.libraryapi.model.Author;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record AuthorDTO(
        UUID id,
        @NotBlank(message = "Campo obrigatório")
        @Size(max = 100, message = "Campo fora do tamanho padrão")
        String name,
        @NotNull(message = "Campo obrigatório")
        @Past(message = "Datas futuras não são permitidas nesse campo")
        LocalDate birthDate,
        @NotBlank(message = "Campo obrigatório")
        @Size(max = 50, message = "Campo fora do tamanho padrão")
        String nationality) {
    public Author createAuthor(){
        Author author = new Author();
        author.setDateOfBirth(this.birthDate);
        author.setName(this.name);
        author.setNationality(this.nationality);

        return author;
    }
}
