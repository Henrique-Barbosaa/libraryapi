package com.henriq.libraryapi.security;

import java.io.IOException;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.henriq.libraryapi.model.User;
import com.henriq.libraryapi.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoginSocialSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private final ObjectProvider<UserService> userServiceProvider;

    public LoginSocialSuccessHandler(ObjectProvider<UserService> userServiceProvider){
        this.userServiceProvider = userServiceProvider;
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
        User user = userServiceProvider.getObject().getByEmail(email);

        CustomAuthentication customAuthentication = new CustomAuthentication(user);
        SecurityContextHolder.getContext().setAuthentication(customAuthentication);

        super.onAuthenticationSuccess(request, response, customAuthentication);
    }
}
