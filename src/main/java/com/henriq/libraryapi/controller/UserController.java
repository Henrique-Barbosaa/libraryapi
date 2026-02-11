package com.henriq.libraryapi.controller;

import com.henriq.libraryapi.dto.UserUpdateDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String update(
            @Valid @RequestBody UserUpdateDTO dto,
            @PathVariable("id") String id
    ){
        userService.update(dto, id);
        return "redirect:/";
    }
}
