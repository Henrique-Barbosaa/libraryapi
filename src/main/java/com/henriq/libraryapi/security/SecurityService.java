package com.henriq.libraryapi.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.henriq.libraryapi.model.User;
import com.henriq.libraryapi.service.UserService;

@Component
public class SecurityService {
    private final UserService userService;

    SecurityService(UserService userService){
        this.userService = userService;
    }

    public User getLoggedUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();

        return userService.getByLogin(userDetails.getUsername());
    }
}
