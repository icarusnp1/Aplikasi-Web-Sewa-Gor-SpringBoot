package com.tugas_besar.segor.controllers;

import com.tugas_besar.segor.entity.Users;
import com.tugas_besar.segor.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {
    private final UserService service;

    public AuthController(UserService service) {
        this.service = service;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(@ModelAttribute Users user, Model model) {
        service.register(user);
        model.addAttribute("message", "Registrasi berhasil, silakan login.");
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam("nama") String nama, @RequestParam("password") String password, Model model) {
        if (service.login(nama, password)) {
            return "redirect:/home";
        } else {
            model.addAttribute("error", "Email atau password salah!");
            return "login";
        }
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/home"; // atau "redirect:/login" jika ingin ke login
    }
}
