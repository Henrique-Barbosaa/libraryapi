package com.henriq.libraryapi.validator;

import com.henriq.libraryapi.exceptions.DuplicateRegistrationException;
import com.henriq.libraryapi.exceptions.InvalidFiedException;
import com.henriq.libraryapi.model.Book;
import com.henriq.libraryapi.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BookValidator {

    private static final int YEAR_WITH_MANDATORY_PRICE = 2020;
    private final BookRepository repository;

    public void validate(Book book){
        if(bookExistByIsbn(book)){
            throw new DuplicateRegistrationException("ISBN já cadastrado no sistema!");
        }
        if(isPriceNull(book)){
            throw new InvalidFiedException("price", "Para livros lançados a partir de 2020, é obrigatório informar o preço.");
        }
    }

    private boolean isPriceNull(Book book) {
        return book.getPublicationDate().getYear() >= YEAR_WITH_MANDATORY_PRICE
                && book.getPrice() == null;
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
