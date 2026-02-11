package com.henriq.libraryapi.controller;

import com.henriq.libraryapi.dto.UserDTO;
import com.henriq.libraryapi.model.Roles;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model){
        model.addAttribute("userDTO", new UserDTO("", ""));
        return "register";
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
