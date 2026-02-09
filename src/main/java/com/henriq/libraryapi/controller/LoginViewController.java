package com.henriq.libraryapi.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginViewController {

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @GetMapping("/authorized")
    @ResponseBody
    public String authorizedPage(@RequestParam("code") String code){
        return "Seu código de autenticação: " + code;
    }
    
    @GetMapping("/")
    @ResponseBody
    public String home(Authentication auth){
        return "Olá " + auth.getName() + ", seja bem-vindo!";
    }
}
