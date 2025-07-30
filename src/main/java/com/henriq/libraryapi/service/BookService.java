package com.henriq.libraryapi.service;

import com.henriq.libraryapi.model.Book;
import com.henriq.libraryapi.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
