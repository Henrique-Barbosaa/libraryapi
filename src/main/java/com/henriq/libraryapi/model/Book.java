package com.henriq.libraryapi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "books")
@Data
@ToString(exclude = "author")
@EntityListeners(AuditingEntityListener.class)
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
    )
    @JoinColumn(name = "author_id")
    private Author author;

    @CreatedDate
    @Column(name = "register_date")
    private LocalDateTime registerDate;

    @LastModifiedDate
    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "user_id")
    private UUID userId;

}
