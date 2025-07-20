package com.henriq.libraryapi.repository;

import com.henriq.libraryapi.model.Author;
import com.henriq.libraryapi.model.Book;
import com.henriq.libraryapi.model.BookGender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@SpringBootTest
public class BookRepositoryTest {
    @Autowired
    private BookRepository repository;

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    void saveBookTest(){
        Book book = new Book();
        book.setTitle("Architecture");
        book.setIsbn("85675");
        book.setGender(BookGender.FANTASY);
        book.setPrice(BigDecimal.valueOf(887));
        book.setPublicationDate(LocalDate.of(1887,10,20));

        Author bookAuthor = authorRepository
                .findById(UUID.fromString("e59cd16c-cc08-4220-9e82-76ddb2c30235"))
                .orElse(null);

        if(bookAuthor != null){
            book.setAuthor(bookAuthor);
            var savedBook = repository.save(book);
            System.out.println("ID: " + savedBook.getId());
            return;
        }
        System.out.println("Livro não foi salvo: não existe um autor com esse ID.");
    }


    /**
     * O Cascade não é recomendado para ser usado no meio profissional,
     * pois pode causar muitos problemas no banco de dados caso o dev
     * não tenha total noção do que está fazendo.
     */
    @Test
    void saveBookWithCascadeTest(){
        Book book = new Book();
        book.setTitle("Entendendo Algorítmos");
        book.setIsbn("21293");
        book.setGender(BookGender.BIOGRAPHY);
        book.setPrice(BigDecimal.valueOf(430));
        book.setPublicationDate(LocalDate.of(1989,4,15));

        Author author = new Author();
        author.setName("Liel");
        author.setNationality("Françes");
        author.setDateOfBirth(LocalDate.of(1910,3,8));

        book.setAuthor(author);

        repository.save(book);
    }

    @Test
    void updateBookAuthor(){
        Book book = repository.findById(UUID.fromString("56e3038a-b8be-44db-b73e-bafb157f88df"))
                .orElse(null);

        Author newBookAuthor = authorRepository
                .findById(UUID.fromString("e59cd16c-cc08-4220-9e82-76ddb2c30235"))
                .orElse(null);

        book.setAuthor(newBookAuthor);
        repository.save(book);
    }

    @Test
    // @Transactional
    void findBookById(){
        Book book = repository.findById(UUID.fromString("0176c098-2c50-4b08-bf8a-ce2e20c4d767"))
                .orElse(null);

        System.out.println("Livro: " + book.getTitle());
        System.out.println("Autor do livro: " + book.getAuthor().getName());
    }

    @Test
    void findAllByTitleContainingIgnoreCase(){
        var list = repository.findByTitleContainingIgnoreCase("ence in");
        list.forEach(System.out::println);
    }

    @Test
    void findAllByTitleAndPrice(){
        var list = repository.findByTitleAndPrice("Clean Code", BigDecimal.valueOf(100));
        list.forEach(System.out::println);
    }

    @Test
    void findAllByTitleOrPrice(){
        var list = repository.findByTitleOrPrice("Clean Code", BigDecimal.valueOf(100));
        list.forEach(System.out::println);
    }

    @Test
    void findAllBetweenDates(){
        var list = repository.findByPublicationDateBetween(
                LocalDate.of(1800, 1, 1),
                LocalDate.of(2020, 4, 14));
        list.forEach(System.out::println);
    }

    @Test
    void orderByPriceJPQL(){
        var books = repository.findAllOrderByPrice();
        books.forEach(System.out::println);
    }

    @Test

    void findBooksAuthorJPQL(){
        var authors = repository.findAllBooksAuthor();
        authors.forEach(System.out::println);
    }

    @Test
    void findByGenderWithParamJPQL(){
        var books = repository.findByGender(BookGender.FANTASY);
        books.forEach(System.out::println);
    }

    @Test
    void deleteByGenderJPQL(){
        repository.deleteByGender(BookGender.FICTION);
    }

    @Test
    void updateAllAuthorsJPQL(){
        var author = authorRepository.findById(UUID.fromString("9377c5c7-aa12-44de-ae16-28790bef1f21")).orElse(null);
        repository.updateAuthor(author);
    }

    @Test
    void findAllBooks(){
        List<Book> allBooks = repository.findAll();
        allBooks.forEach(System.out::println);
    }

    @Test
    void deleteAllBooks(){
        repository.deleteAll();
    }
}
