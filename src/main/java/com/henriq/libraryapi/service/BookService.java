package com.henriq.libraryapi.service;

import com.henriq.libraryapi.model.Book;
import com.henriq.libraryapi.model.BookGender;
import com.henriq.libraryapi.repository.BookRepository;
import com.henriq.libraryapi.repository.specs.BookSpecs;
import com.henriq.libraryapi.security.SecurityService;
import com.henriq.libraryapi.validator.BookValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class BookService {

    private final BookRepository  repository;
    private final BookValidator validator;
    private final SecurityService securityService;

    public BookService(
        BookRepository bookRepository,
        BookValidator bookValidator,
        SecurityService securityService
    ){
        this.repository = bookRepository;
        this.validator = bookValidator;
        this.securityService = securityService;
    }

    public Book save(Book book){
        validator.validate(book);
        book.setUser(securityService.getLoggedUser());
        return repository.save(book);
    }

    public Optional<Book> findById(UUID id){
        return repository.findById(id);
    }

    public void delete(Book book){
        repository.delete(book);
    }

    public Page<Book> search(String isbn,
                             String title,
                             String authorName,
                             BookGender gender,
                             Integer publicationYear,
                             Integer page,
                             Integer pageSize){
        Specification<Book> specs = (root, query, cb) -> cb.conjunction();

        if (isbn != null) specs = specs.and(BookSpecs.isbnEqual(isbn));
        if (title != null) specs = specs.and(BookSpecs.titleLike(title));
        if (gender != null) specs = specs.and(BookSpecs.genderEqual(gender));
        if(publicationYear != null) specs = specs.and(BookSpecs.publicationYearEqual(publicationYear));
        if(authorName != null) specs = specs.and(BookSpecs.authorNameLike(authorName));

        return repository.findAll(specs, PageRequest.of(page, pageSize));
    }
}
