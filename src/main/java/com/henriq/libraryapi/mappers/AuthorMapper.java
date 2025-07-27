package com.henriq.libraryapi.mappers;


import com.henriq.libraryapi.dto.AuthorDTO;
import com.henriq.libraryapi.model.Author;
import org.mapstruct.Mapper;
@Mapper(componentModel = "spring")
public interface AuthorMapper {

    Author toEntity(AuthorDTO dto);
    AuthorDTO toDTO(Author author);
}
