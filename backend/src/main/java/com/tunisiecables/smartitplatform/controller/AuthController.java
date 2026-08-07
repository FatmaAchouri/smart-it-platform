package com.tunisiecables.smartitplatform.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String email,
                           @RequestParam String password,
                           @RequestParam(required = false) String fullName,
                           @RequestParam(required = false) String role,
                           Model model) {
        // Ici tu peux sauvegarder l'utilisateur dans la base
        // Pour le moment on affiche un message
        model.addAttribute("message", "Compte créé avec succès pour " + username + " !");
        return "register";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
}