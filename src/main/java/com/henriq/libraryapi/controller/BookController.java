package com.henriq.libraryapi.controller;


import com.henriq.libraryapi.dto.BookRequestDTO;
import com.henriq.libraryapi.dto.ResponseError;
import com.henriq.libraryapi.exceptions.DuplicateRegistrationException;
import com.henriq.libraryapi.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/livros")
@RequiredArgsConstructor
public class BookController {

    private BookService service;

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid BookRequestDTO bookDTO){
        try{
            return ResponseEntity.ok(bookDTO);
        } catch (DuplicateRegistrationException e){
            var errorDTO = ResponseError.conflictResponse(e.getMessage());
            return ResponseEntity.status(errorDTO.status()).body(errorDTO);
        }
    }
}
