package com.henriq.libraryapi.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.henriq.libraryapi.model.User;
import com.henriq.libraryapi.service.UserService;

public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public CustomUserDetailsService(UserService userService){
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username){
        User user = userService.getByLogin(username);

        return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .roles(user.getRoles().stream()
                        .map(role -> role.toString())
                        .toArray(String[]::new))
                    .build();
    }
    
}
