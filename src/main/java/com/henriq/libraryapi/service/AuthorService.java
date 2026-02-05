package com.henriq.libraryapi.service;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.henriq.libraryapi.exceptions.OperationNotAllowedException;
import com.henriq.libraryapi.model.Author;
import com.henriq.libraryapi.repository.AuthorRepository;
import com.henriq.libraryapi.repository.BookRepository;
import com.henriq.libraryapi.security.SecurityService;
import com.henriq.libraryapi.validator.AuthorValidator;

@Service
public class AuthorService {
    private final AuthorRepository repository;
    private final BookRepository bookRepository;
    private final AuthorValidator validator;
    private final SecurityService securityService;

    public AuthorService(
        AuthorRepository repository, 
        AuthorValidator validator, 
        BookRepository bookRepository,
        SecurityService securityService
    ){
        this.validator = validator;
        this.bookRepository = bookRepository;
        this.repository = repository;
        this.securityService = securityService;
    }

    public Author save(Author author){
        validator.validate(author);
        author.setUser(securityService.getLoggedUser());
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

    public List<Author> searchByExample(String name, String nationality){
        Author author = new Author();
        author.setNationality(nationality);
        author.setName(name);

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Author> authorExample = Example.of(author, matcher);

        return repository.findAll(authorExample);
    }

    public boolean hasABook(Author author){
        return bookRepository.existsByAuthor(author);
    }
}
