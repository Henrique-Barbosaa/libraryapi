package com.henriq.libraryapi.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.henriq.libraryapi.model.Client;

public interface ClientRepostitory extends JpaRepository<Client, UUID> {

    Optional<Client> findByClientId(String clientId);
}
