package com.henriq.libraryapi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "authors", schema = "public")
@Data
@ToString(exclude = "books")
@EntityListeners(AuditingEntityListener.class)
public class Author {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "birth_date", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "nationality", length = 50, nullable = false)
    private String nationality;

    @OneToMany(
            mappedBy = "author"
    )
    private List<Book> books;

    @CreatedDate
    @Column(name = "register_date")
    private LocalDateTime registerDate;

    @LastModifiedDate
    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "user_id")
    private UUID userId;

}
