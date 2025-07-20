package com.henriq.libraryapi.repository;

import com.henriq.libraryapi.model.Author;
import com.henriq.libraryapi.model.Book;
import com.henriq.libraryapi.model.BookGender;
import com.henriq.libraryapi.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AuthorRepositoryTest {
    @Autowired
    private AuthorRepository repository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TransactionService transactionService;

    @Test
    public void saveAuthorTest(){
        Author author = new Author();
        author.setName("José");
        author.setNationality("Espanhol");
        author.setDateOfBirth(LocalDate.of(2001,7,20));

        var savedAuthor = repository.save(author);
        System.out.println("ID: " + savedAuthor.getId());
    }

    @Test
    public void saveAuthorWithBooksTest(){
        Author author = new Author();
        author.setName("Leonardo");
        author.setNationality("Argentino");
        author.setDateOfBirth(LocalDate.of(1999,7,30));

        Book book2 = new Book();
        book2.setTitle("Dependence Inversion");
        book2.setIsbn("877765");
        book2.setGender(BookGender.FICTION);
        book2.setPrice(BigDecimal.valueOf(620));
        book2.setPublicationDate(LocalDate.of(2021,6,5));
        book2.setAuthor(author);

        Book book = new Book();
        book.setTitle("Dependence Injection");
        book.setIsbn("76543");
        book.setGender(BookGender.FANTASY);
        book.setPrice(BigDecimal.valueOf(567));
        book.setPublicationDate(LocalDate.of(2020,4,15));
        book.setAuthor(author);

        author.setBooks(new ArrayList<>());
        author.getBooks().add(book);
        author.getBooks().add(book2);

        repository.save(author);
        bookRepository.saveAll(author.getBooks());
    }

    @Test
    public void updateAuthorTest(){
        var id = UUID.fromString("83b28be8-f0dc-4500-8dfe-3b122b4c59f3");
        Optional<Author> possiblyAuthor = repository.findById(id);

        if(possiblyAuthor.isPresent()){
            Author author = possiblyAuthor.get();

            author.setName("Arthur Moura");
            author.setNationality("Cazaque");

            System.out.println("Autor atualizado com as mudanças: " + author);
            repository.save(author);
            return;
        }
        System.out.println("ID inválido.");
    }

    @Test
    public void showAllBooks(){
        var id = UUID.fromString("9377c5c7-aa12-44de-ae16-28790bef1f21");
        Author author = repository.findById(id).orElse(null);

        var books = bookRepository.findByAuthor(author);
        author.setBooks(books);
        author.getBooks().forEach(System.out::println);

    }

    @Test
    public void testTransaction(){
        // transactionService.rollbackTest();

        transactionService.updateTransactionTest();
    }

    @Test
    public void findAllAuthors(){
        List<Author> allAuthors = repository.findAll();
        allAuthors.forEach(System.out::println);
    }

    @Test
    public void deleteAllAuthors(){
        repository.deleteAll();
    }
}
