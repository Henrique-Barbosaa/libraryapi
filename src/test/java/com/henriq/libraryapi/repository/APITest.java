package com.henriq.libraryapi.repository;


import com.henriq.libraryapi.model.Book;
import com.henriq.libraryapi.model.Author;
import com.henriq.libraryapi.model.BookGender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;


@SpringBootTest
public class APITest {

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    BookRepository repository;

    @Test
    void saveTest(){
        Book book = new Book();
        book.setIsbn("11111");
        book.setPrice(BigDecimal.valueOf(100));
        book.setPublicationDate(LocalDate.of(1898, 1, 2));
        book.setTitle("Java");
        book.setGender(BookGender.FICCAO);

        Author author = authorRepository
                .findById(UUID.fromString("21e07b65-2712-4d94-be18-55a769e5f300"))
                .orElse(null);

        book.setAuthor(author);
        repository.save(book);
    }
}
