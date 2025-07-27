package com.henriq.libraryapi.controller;


import com.henriq.libraryapi.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/livros")
@RequiredArgsConstructor
public class BookController {

    private BookService service;
}
