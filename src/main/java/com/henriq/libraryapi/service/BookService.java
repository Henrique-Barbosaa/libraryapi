package com.henriq.libraryapi.service;

import com.henriq.libraryapi.model.Book;
import com.henriq.libraryapi.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository  repository;

    public Book save(Book book){
        return repository.save(book);
    }
}
