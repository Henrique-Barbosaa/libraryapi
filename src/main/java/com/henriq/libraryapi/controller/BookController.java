package com.henriq.libraryapi.controller;


import com.henriq.libraryapi.dto.BookRequestDTO;
import com.henriq.libraryapi.dto.BookResponseDTO;
import com.henriq.libraryapi.mappers.BookMapper;
import com.henriq.libraryapi.model.Book;
import com.henriq.libraryapi.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/livros")
@RequiredArgsConstructor
public class BookController implements GenericController {
    private final BookService service;
    private final BookMapper mapper;

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid BookRequestDTO bookDTO) {
        Book book = mapper.toEntity(bookDTO);
        service.save(book);
        URI location = generateHeaderLocation(book.getId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> getDetails(@PathVariable("id") String id){
        return service
                .findById(UUID.fromString(id))
                .map(book -> {
                    BookResponseDTO dto = mapper.toDTO(book);
                    return ResponseEntity.ok(dto);
                })
                .orElseGet( () -> ResponseEntity.notFound().build() );
    }
}
