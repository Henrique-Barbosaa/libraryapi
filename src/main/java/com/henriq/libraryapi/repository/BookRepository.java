package com.henriq.libraryapi.repository;


import com.henriq.libraryapi.model.Author;
import com.henriq.libraryapi.model.Book;
import com.henriq.libraryapi.model.BookGender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {

    //QUERY METHODS

    // select * from book where id_author = author.id
    List<Book> findByAuthor(Author author);

    List<Book> findByTitleContainingIgnoreCase(String title);

    List<Book> findByTitleContaining(String title);

    // select * from book where book.title = ? AND price = ?
    List<Book> findByTitleAndPrice(String title, BigDecimal price);

    // select * from book where title = ? OR price = ?
    List<Book> findByTitleOrPrice(String title, BigDecimal price);

    // select * from book where publication_date BETWEEN ? and ?
    List<Book> findByPublicationDateBetween(LocalDate start, LocalDate end);


    // JPQL QUERY
    @Query("""
            select l
            from Book as l
            order by l.price
            """)
    List<Book> findAllOrderByPrice();

    @Query("""
            select a
            from Book as l
            join l.author a
            """)
    List<Author> findAllBooksAuthor();


    // NAMED PARAM
    @Query("""
            select l
            from Book l
            where l.gender = :gender
            """)
    List<Book> findByGender(@Param("gender") BookGender gender);


    @Modifying
    @Transactional
    @Query("""
            delete
            from Book
            where gender = ?1
            """)
    void deleteByGender(BookGender gender);

    @Modifying
    @Transactional
    @Query("""
            update Book
            set author = ?1
            """)
    void updateAuthor(Author author);
}
