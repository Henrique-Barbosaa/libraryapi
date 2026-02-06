package com.henriq.libraryapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.henriq.libraryapi.dto.ClientDTO;
import com.henriq.libraryapi.mappers.ClientMapper;
import com.henriq.libraryapi.model.Client;
import com.henriq.libraryapi.service.ClientService;

@RestController
@RequestMapping("/clients")
public class ClientController {
    private final ClientService clientService;
    private final ClientMapper clientMapper;

    public ClientController(ClientService clientService, ClientMapper clientMapper){
        this.clientService = clientService;
        this.clientMapper = clientMapper;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Client> save(@RequestBody ClientDTO dto){
        Client savedClient = clientService.save(clientMapper.toEntity(dto));
        return ResponseEntity.created(null).body(savedClient);
    }
}
