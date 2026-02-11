package com.henriq.libraryapi.dto;

import com.henriq.libraryapi.model.Roles;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UserUpdateDTO(

    @NotBlank(message = "Senha é obrigatória")
    String password, 
    
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    String email,


    @NotNull(message = "Campo obrigatório")
    List<Roles> roles
) {}
