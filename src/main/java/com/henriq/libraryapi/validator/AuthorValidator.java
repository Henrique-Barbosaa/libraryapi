package com.henriq.libraryapi.validator;

import com.henriq.libraryapi.exceptions.DuplicateRegistrationException;
import com.henriq.libraryapi.model.Author;
import com.henriq.libraryapi.repository.AuthorRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthorValidator {
    private AuthorRepository repository;

    public AuthorValidator(AuthorRepository repository) {
        this.repository = repository;
    }

    public void validate(Author author){
        if (authorAlreadyExist(author)){
            throw new DuplicateRegistrationException("Autor já cadastrado no sistema!");
        }
    }

    public boolean authorAlreadyExist(Author author){
        Optional<Author> authorFound = repository.findByNameAndDateOfBirthAndNationality(
                author.getName(), author.getDateOfBirth(), author.getNationality()
        );

        if(author.getId() == null) return authorFound.isPresent();

        if (authorFound.isPresent()) return !author.getId().equals(authorFound.get().getId());

        return false;
    }
}
