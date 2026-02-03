package com.henriq.libraryapi.dto;

import java.util.List;
import com.henriq.libraryapi.model.Roles;

public record UserDTO(String username, String password, List<Roles> roles) {}
