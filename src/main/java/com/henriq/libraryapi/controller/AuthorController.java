package com.henriq.libraryapi.controller;


import com.henriq.libraryapi.dto.AuthorDTO;
import com.henriq.libraryapi.dto.ResponseError;
import com.henriq.libraryapi.exceptions.DuplicateRegistrationException;
import com.henriq.libraryapi.exceptions.OperationNotAllowedException;
import com.henriq.libraryapi.model.Author;
import com.henriq.libraryapi.service.AuthorService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
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
    public ResponseEntity<Object> save(@RequestBody @Valid AuthorDTO author){
        try {
            Author authorEntity = author.createAuthor();
            service.save(authorEntity);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(authorEntity.getId())
                    .toUri();

            return ResponseEntity.created(location).build();
        } catch (DuplicateRegistrationException e){
            var errorDTO = ResponseError.conflictResponse(e.getMessage());
            return ResponseEntity.status(errorDTO.status()).body(errorDTO);
        }
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
    public ResponseEntity<Object> delete(@PathVariable("id") String id){
        try{
            var idAuthor = UUID.fromString(id);
            Optional<Author> authorOpt = service.getById(idAuthor);

            if(authorOpt.isEmpty()) return ResponseEntity.notFound().build();
            service.delete(authorOpt.get());
            return ResponseEntity.noContent().build();
        } catch (OperationNotAllowedException e){
            var errorDTO = ResponseError.defaultResponse(e.getMessage());
            return ResponseEntity.status(errorDTO.status()).body(errorDTO);
        }
    }

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> search(
            @RequestParam(value = "nationality", required = false)
            String nationality,
            @RequestParam(value = "name", required = false)
            String name){
        List<Author> authors = service.searchByExample(name, nationality);
        List<AuthorDTO> authorsDTO = authors
                .stream()
                .map(author -> new AuthorDTO(
                        author.getId(),
                        author.getName(),
                        author.getDateOfBirth(),
                        author.getNationality()))
                .toList();

        return ResponseEntity.ok(authorsDTO);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> update(
            @PathVariable("id") String id,
            @RequestBody @Valid AuthorDTO dto){

        try{
            Optional<Author> authorOpt = service.getById(UUID.fromString(id));
            if(authorOpt.isEmpty()) return ResponseEntity.notFound().build();

            var author = authorOpt.get();
            author.setNationality(dto.nationality());
            author.setName(dto.name());
            author.setDateOfBirth(dto.birthDate());
            service.save(author);

            return ResponseEntity.noContent().build();
        } catch (DuplicateRegistrationException e){
            var errorDTO = ResponseError.conflictResponse(e.getMessage());
            return ResponseEntity.status(errorDTO.status()).body(errorDTO);
        }
    }
}
