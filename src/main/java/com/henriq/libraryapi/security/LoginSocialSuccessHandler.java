package com.henriq.libraryapi.security;

import java.io.IOException;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.henriq.libraryapi.mappers.UserMapper;
import com.henriq.libraryapi.model.Roles;
import com.henriq.libraryapi.model.User;
import com.henriq.libraryapi.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoginSocialSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private static final String DEFAULT_PASSWORD = "123456";

    private final UserService userService;
    private final UserMapper userMapper;

    public LoginSocialSuccessHandler(UserService userService, UserMapper mapper){
        this.userService = userService;
        this.userMapper = mapper;
    }

    @Override
    public void onAuthenticationSuccess(
        HttpServletRequest request, 
        HttpServletResponse response,
        Authentication authentication
    ) throws ServletException, IOException {

        OAuth2AuthenticationToken auth2AuthToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User auth2User = auth2AuthToken.getPrincipal();

        String email = auth2User.getAttribute("email");
        User user = userService.getByEmail(email);

        if(user == null){
            user = new User();
            user.setEmail(email);
            user.setPassword(DEFAULT_PASSWORD);
            user.setRoles(List.of(Roles.USER));

            userService.save(userMapper.toDTO(user));
        }

        CustomAuthentication customAuthentication = new CustomAuthentication(user);
        SecurityContextHolder.getContext().setAuthentication(customAuthentication);

        super.onAuthenticationSuccess(request, response, customAuthentication);
    }
}
