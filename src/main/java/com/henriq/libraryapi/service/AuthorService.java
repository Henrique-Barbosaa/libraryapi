package com.henriq.libraryapi.service;


import com.henriq.libraryapi.model.Author;
import com.henriq.libraryapi.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthorService {
    private final AuthorRepository repository;

    public AuthorService(AuthorRepository repository){
        this.repository = repository;
    }

    public Author save(Author author){
        return this.repository.save(author);
    }

    public Optional<Author> getById(UUID id){
        return this.repository.findById(id);
    }

    public void delete(Author author){
        this.repository.delete(author);
    }

    public List<Author> search(String name, String nationality){
        if(name != null && nationality != null) return this.repository.findByNameAndNationality(name, nationality);
        if(name != null) return this.repository.findByName(name);
        if(nationality != null) return this.repository.findByNationality(nationality);

        return repository.findAll();
    }
}
