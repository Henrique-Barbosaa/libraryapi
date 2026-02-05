package com.henriq.libraryapi.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.henriq.libraryapi.model.User;

@Component
public class SecurityService {

    public User getLoggedUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof CustomAuthentication customAuth) return customAuth.getUser();

        return null;
    }
}
