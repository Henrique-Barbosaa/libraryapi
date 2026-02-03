package com.henriq.libraryapi.dto;

import java.util.List;
import com.henriq.libraryapi.model.Roles;

public record UserDTO(String login, String password, List<Roles> roles) {}
