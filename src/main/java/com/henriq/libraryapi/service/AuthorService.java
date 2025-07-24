package com.henriq.libraryapi.service;


import com.henriq.libraryapi.exceptions.OperationNotAllowedException;
import com.henriq.libraryapi.model.Author;
import com.henriq.libraryapi.repository.AuthorRepository;
import com.henriq.libraryapi.repository.BookRepository;
import com.henriq.libraryapi.validator.AuthorValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthorService {
    private final AuthorRepository repository;
    private final BookRepository bookRepository;
    private final AuthorValidator validator;

    public AuthorService(AuthorRepository repository, AuthorValidator validator, BookRepository bookRepository){
        this.validator = validator;
        this.bookRepository = bookRepository;
        this.repository = repository;
    }

    public Author save(Author author){
        validator.validate(author);
        return this.repository.save(author);
    }

    public Optional<Author> getById(UUID id){
        return this.repository.findById(id);
    }

    public void delete(Author author){
        if(hasABook(author)){
            throw new OperationNotAllowedException("Autor(a) possui um livro cadastrado no sistema.");
        }
        this.repository.delete(author);
    }

    public List<Author> search(String name, String nationality){
        if(name != null && nationality != null) return this.repository.findByNameAndNationality(name, nationality);
        if(name != null) return this.repository.findByName(name);
        if(nationality != null) return this.repository.findByNationality(nationality);

        return repository.findAll();
    }

    public boolean hasABook(Author author){
        return bookRepository.existsByAuthor(author);
    }
}
