package com.henriq.libraryapi.service;


import com.henriq.libraryapi.model.Author;
import com.henriq.libraryapi.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthorService {
    private final AuthorRepository repository;

    public AuthorService(AuthorRepository repository){
        this.repository = repository;
    }

    public Author save(Author author){
        return repository.save(author);
    }

    public Optional<Author> getById(UUID id){
        return repository.findById(id);
    }
}
