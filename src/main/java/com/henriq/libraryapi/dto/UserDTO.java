package com.henriq.libraryapi.dto;

import java.util.List;
import com.henriq.libraryapi.model.Roles;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserDTO(

    @NotBlank(message = "Campo obrigatório")
    String password, 
    
    @NotBlank(message = "Campo obrigatório")
    @Email(message = "Email inválido")
    String email, 

    @NotNull(message = "Campo obrigatório")
    List<Roles> roles
) {}
