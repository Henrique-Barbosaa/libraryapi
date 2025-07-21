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
@Table(name = "autor", schema = "public")
@Data
@ToString(exclude = "books")
@EntityListeners(AuditingEntityListener.class)
public class Author {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nome", length = 100, nullable = false)
    private String name;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "nacionalidade", length = 50, nullable = false)
    private String nationality;

    @OneToMany(
            mappedBy = "author"
            // fetch = FetchType.EAGER
    )
    private List<Book> books;

    @CreatedDate
    @Column(name = "data_cadastro")
    private LocalDateTime registerDate;

    @LastModifiedDate
    @Column(name = "data_atualizacao")
    private LocalDateTime updateDate;

    @Column(name = "id_usuario")
    private UUID userId;

}
