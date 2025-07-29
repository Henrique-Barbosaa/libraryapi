package com.henriq.libraryapi.mappers;


import com.henriq.libraryapi.dto.BookRequestDTO;
import com.henriq.libraryapi.model.Book;
import com.henriq.libraryapi.repository.AuthorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class BookMapper {

    @Autowired
    AuthorRepository authorRepository;

    @Mapping(target = "author", expression = "java( authorRepository.findById(dto.id_author()).orElse(null) )")
    public abstract Book toEntity(BookRequestDTO dto);
}
