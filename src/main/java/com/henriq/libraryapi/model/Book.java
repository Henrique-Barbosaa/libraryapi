package com.henriq.libraryapi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "book")
@Data
@ToString(exclude = "author")
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "title", length = 150, nullable = false)
    private String title;

    @Column(name = "isbn", length = 20, nullable = false)
    private String isbn;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", length = 30, nullable = false)
    private BookGender gender;

    @Column(name = "publication_date", nullable = false)
    private LocalDate publicationDate;

    @Column(name = "price", precision = 18, scale = 2)
    private BigDecimal price;

    @ManyToOne(
            fetch = FetchType.LAZY
          //  cascade = CascadeType.ALL
    )
    @JoinColumn(name = "id_author")
    private Author author;

}
