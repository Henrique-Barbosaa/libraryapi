package com.henriq.libraryapi.security;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.henriq.libraryapi.model.User;

import lombok.Getter;

@Getter
public class CustomAuthentication implements Authentication{

    private final User user;

    public CustomAuthentication(User user){
        this.user = user;
    }

    @Override
    public String getName() {
        return this.user.getUsername();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.user.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority(role.toString()))
            .toList();
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return this.user.getUsername();
    }

    @Override
    public Object getPrincipal() {
        return this.user;
    }

    @Override
    public boolean isAuthenticated() {
        return this.user != null;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {}
    
}
