package com.henriq.libraryapi.dto;

public record ClientDTO(
    String clientId, 
    String clientSecret, 
    String redirectUri, 
    String scope
) {}
