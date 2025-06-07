package com.tugas_besar.segor.controllers;

import com.tugas_besar.segor.entity.Users;
import com.tugas_besar.segor.service.UserService;
import jakarta.servlet.http.HttpSession;
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
        return "logreg/login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "logreg/register";
    }

    @PostMapping("/register")
    public String doRegister(@ModelAttribute Users user, Model model) {
        service.register(user);
        model.addAttribute("message", "Registrasi berhasil, silakan login.");
        return "logreg/login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam("nama") String nama,
                          @RequestParam("password") String password,
                          Model model,
                          HttpSession session) {
        Users user = service.findByNamaAndPassword(nama, password);
        if (user != null) {
            session.setAttribute("user", user);
            session.setAttribute("role", user.getRole());
            return "redirect:/home";
        } else {
            model.addAttribute("error", "Email atau password salah!");
            return "logreg/login";
        }
    }

    @GetMapping("/home")
    public String home(HttpSession session, Model model) {
        Users user = (Users) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "logreg/home";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/home";
    }
}