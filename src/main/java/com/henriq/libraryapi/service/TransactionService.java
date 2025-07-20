package com.henriq.libraryapi.service;


import com.henriq.libraryapi.model.Author;
import com.henriq.libraryapi.model.Book;
import com.henriq.libraryapi.model.BookGender;
import com.henriq.libraryapi.repository.AuthorRepository;
import com.henriq.libraryapi.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;


/**
 * begin -> inicia a transação
 * Commit -> Confirma as alterações e finaliza a transação
 * Rollback -> Desfaz as alterações e finaliza a transação
 * Se der uma exception durante a transação, ele da Rollback.
 * Caso ocorra tudo certo, ele da Commit.
 */
@Service
public class TransactionService {
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    /**
     * Dentro de uma transação, não precisa usar o metodo SAVE,
     * pois a transação COMMITA as mudanças ao finalizar a função sem erro.
     */
    @Transactional
    public void updateTransactionTest(){
        var author = authorRepository
                .findById(UUID.fromString("73a65fd3-31c3-4728-92af-19d9fbc29dec"))
                .orElse(null);
        author.setDateOfBirth(LocalDate.of(2000, 2, 10));
    }

    /**
     * Caso a função lançe uma exception, a transação da ROLLBACK,
     * e NÃO COMMITA as mudanças.
     */
    @Transactional
    public void rollbackTest(){
        Author author = new Author();
        author.setName("Liel");
        author.setNationality("Françesa");
        author.setDateOfBirth(LocalDate.of(1980,3,8));

        authorRepository.save(author);

        Book book = new Book();
        book.setTitle("Entendendo Algorítmos");
        book.setIsbn("21293");
        book.setGender(BookGender.BIOGRAPHY);
        book.setPrice(BigDecimal.valueOf(430));
        book.setPublicationDate(LocalDate.of(1989,4,15));

        book.setAuthor(author);
        bookRepository.save(book);

        if(author.getName().equals("Liel")) throw new RuntimeException("Rollaack!");
    }
}
