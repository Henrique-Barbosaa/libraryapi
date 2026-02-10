package com.henriq.libraryapi.security;

import com.henriq.libraryapi.model.User;
import com.henriq.libraryapi.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class CustomJwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserService userService;

    public CustomJwtAuthenticationFilter(UserService userService){
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth instanceof JwtAuthenticationToken) {
            User user = userService.getByEmail(auth.getName());
            if(user != null) {
                CustomAuthentication customAuth = new CustomAuthentication(user);
                SecurityContextHolder.getContext().setAuthentication(customAuth);
            }
        }

        filterChain.doFilter(request, response);
    }
}
