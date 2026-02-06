package com.henriq.libraryapi.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.henriq.libraryapi.model.Client;
import com.henriq.libraryapi.repository.ClientRepostitory;

@Service
public class ClientService {
    
    private final ClientRepostitory clientRepository;
    private final PasswordEncoder passwordEncoder;
    
    public ClientService(ClientRepostitory clientRepository, PasswordEncoder passwordEncoder){
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Client save(Client client){
        client.setClientSecret(passwordEncoder.encode(client.getClientSecret()));
        return clientRepository.save(client);
    }

    public Client getByClientId(String clientId){
        return clientRepository.findByClientId(clientId)
            .orElse(null); 
    }
}
