package com.henriq.libraryapi.controller;


import com.henriq.libraryapi.dto.AuthorDTO;
import com.henriq.libraryapi.model.Author;
import com.henriq.libraryapi.service.AuthorService;
import jakarta.servlet.Servlet;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/autores")
public class AuthorController {
    private final AuthorService service;

    public AuthorController(AuthorService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody AuthorDTO author){
        Author authorEntity = author.createAuthor();
        service.save(authorEntity);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(authorEntity.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<AuthorDTO> getDetails(@PathVariable("id") String id){
        var idAuthor = UUID.fromString(id);
        Optional<Author> authorOpt = service.getById(idAuthor);

        if(authorOpt.isPresent()){
            Author author = authorOpt.get();
            AuthorDTO dto = new AuthorDTO(
                    author.getId(),
                    author.getName(),
                    author.getDateOfBirth(),
                    author.getNationality());

            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id){
        var idAuthor = UUID.fromString(id);
        Optional<Author> authorOpt = service.getById(idAuthor);

        if(authorOpt.isEmpty()) return ResponseEntity.notFound().build();
        service.delete(authorOpt.get());
        return ResponseEntity.noContent().build();
    }
}
