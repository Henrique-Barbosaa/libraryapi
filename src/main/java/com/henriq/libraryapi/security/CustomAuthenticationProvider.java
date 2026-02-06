package com.henriq.libraryapi.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.henriq.libraryapi.model.User;
import com.henriq.libraryapi.service.UserService;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider{

    private final UserService userService;
    private final PasswordEncoder encoder;

    public CustomAuthenticationProvider(UserService userService, PasswordEncoder encoder) {
        this.userService = userService;
        this.encoder = encoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        User user = userService.getByEmail(authentication.getName());
        if (user == null) throw new UsernameNotFoundException("Usuário ou senha incorretos.");
        
        String cryptPassword = user.getPassword();
        String password = authentication.getCredentials().toString();

        boolean passwordMatches = encoder.matches(password, cryptPassword);

        if (passwordMatches) { 
            return new CustomAuthentication(user);
        }

        throw new UsernameNotFoundException("Usuário ou senha incorretos.");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
    
}
