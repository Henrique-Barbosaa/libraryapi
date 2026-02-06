package com.henriq.libraryapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.henriq.libraryapi.dto.UserDTO;
import com.henriq.libraryapi.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UserController {
    private final UserService userService;

    UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid UserDTO userDTO){
        userService.save(userDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
