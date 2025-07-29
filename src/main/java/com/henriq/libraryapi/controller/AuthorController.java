package com.henriq.libraryapi.controller;


import com.henriq.libraryapi.dto.AuthorDTO;
import com.henriq.libraryapi.dto.ResponseError;
import com.henriq.libraryapi.exceptions.DuplicateRegistrationException;
import com.henriq.libraryapi.exceptions.OperationNotAllowedException;
import com.henriq.libraryapi.mappers.AuthorMapper;
import com.henriq.libraryapi.model.Author;
import com.henriq.libraryapi.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/autores")
public class AuthorController implements GenericController {
    private final AuthorService service;
    private final AuthorMapper mapper;

    public AuthorController(AuthorService service, AuthorMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid AuthorDTO dto) {
        Author author = mapper.toEntity(dto);
        service.save(author);
        URI location = generateHeaderLocation(author.getId());

        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<AuthorDTO> getDetails(@PathVariable("id") String id) {
        var idAuthor = UUID.fromString(id);
        Optional<Author> authorOpt = service.getById(idAuthor);

        return service
                .getById(idAuthor)
                .map(author -> {
                    AuthorDTO dto = mapper.toDTO(author);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        var idAuthor = UUID.fromString(id);
        Optional<Author> authorOpt = service.getById(idAuthor);

        if (authorOpt.isEmpty()) return ResponseEntity.notFound().build();
        service.delete(authorOpt.get());

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> search(
            @RequestParam(value = "nationality", required = false)
            String nationality,
            @RequestParam(value = "name", required = false)
            String name) {
        List<Author> authors = service.searchByExample(name, nationality);
        List<AuthorDTO> authorsDTO = authors
                .stream()
                .map(mapper::toDTO)
                .toList();

        return ResponseEntity.ok(authorsDTO);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> update(
            @PathVariable("id") String id,
            @RequestBody @Valid AuthorDTO dto) {
        Optional<Author> authorOpt = service.getById(UUID.fromString(id));
        if (authorOpt.isEmpty()) return ResponseEntity.notFound().build();

        var author = authorOpt.get();
        author.setNationality(dto.nationality());
        author.setName(dto.name());
        author.setDateOfBirth(dto.dateOfBirth());
        service.save(author);

        return ResponseEntity.noContent().build();
    }
}
