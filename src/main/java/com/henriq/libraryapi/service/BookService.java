package com.henriq.libraryapi.service;

import com.henriq.libraryapi.model.Book;
import com.henriq.libraryapi.model.BookGender;
import com.henriq.libraryapi.repository.BookRepository;
import com.henriq.libraryapi.repository.specs.BookSpecs;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository  repository;

    public Book save(Book book){
        return repository.save(book);
    }

    public Optional<Book> findById(UUID id){
        return repository.findById(id);
    }

    public void delete(Book book){
        repository.delete(book);
    }

    public List<Book> search(String isbn,
                             String title,
                             String authorName,
                             BookGender gender,
                             Integer publicationYear){
        Specification<Book> specs = (root, query, cb) -> cb.conjunction();

        if (isbn != null) specs = specs.and(BookSpecs.isbnEqual(isbn));
        if (title != null) specs = specs.and(BookSpecs.titleLike(title));
        if (gender != null) specs = specs.and(BookSpecs.genderEqual(gender));
        if(publicationYear != null) specs = specs.and(BookSpecs.publicationYearEqual(publicationYear));
        if(authorName != null) specs = specs.and(BookSpecs.authorNameLike(authorName));

        return repository.findAll(specs);
    }
}
