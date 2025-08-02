package com.henriq.libraryapi.validator;

import com.henriq.libraryapi.exceptions.DuplicateRegistrationException;
import com.henriq.libraryapi.model.Book;
import com.henriq.libraryapi.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BookValidator {
    private final BookRepository repository;

    public void validate(Book book){
        if (bookExistByIsbn(book)){
            throw new DuplicateRegistrationException("ISBN já cadastrado no sistema!");
        }
    }

    public boolean bookExistByIsbn(Book book){
        Optional<Book> foundBook = repository.findByIsbn(book.getIsbn());

        if(book.getId() == null) return foundBook.isPresent();
        return foundBook
                .stream()
                .map(Book::getId)
                .anyMatch(id -> !id.equals(book.getId()));
    }
}
