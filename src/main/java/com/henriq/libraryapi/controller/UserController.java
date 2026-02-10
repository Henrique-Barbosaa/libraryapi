package com.henriq.libraryapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.henriq.libraryapi.dto.UserDTO;
import com.henriq.libraryapi.service.UserService;

import jakarta.validation.Valid;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public String save(
            @Valid @ModelAttribute("userDTO") UserDTO userDTO,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ){
        if(result.hasErrors()) return "register";

        userService.save(userDTO);

        redirectAttributes.addFlashAttribute("successMessage", "Conta criada com sucesso! Faça login.");
        return "redirect:/login";
    }
}
