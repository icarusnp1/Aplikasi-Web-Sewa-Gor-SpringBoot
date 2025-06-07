package com.tugas_besar.segor.controllers;

import com.tugas_besar.segor.entity.Users;
import com.tugas_besar.segor.service.UserService;
import com.tugas_besar.segor.service.GorService;
import com.tugas_besar.segor.service.LapanganService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {
    private final UserService userService;
    private final GorService gorService;
    private final LapanganService lapanganService;

    public AuthController(UserService userService, GorService gorService, LapanganService lapanganService) {
        this.userService = userService;
        this.gorService = gorService;
        this.lapanganService = lapanganService;
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
        userService.register(user);
        model.addAttribute("message", "Registrasi berhasil, silakan login.");
        return "logreg/login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam("nama") String nama,
                        @RequestParam("password") String password,
                        Model model,
                        HttpSession session) {
        Users user = userService.findByNamaAndPassword(nama, password);
        if (user != null) {
            session.setAttribute("user", user);
            session.setAttribute("role", user.getRole());
            // Redirect berdasarkan role
            if ("user".equalsIgnoreCase(user.getRole())) {
                return "redirect:/user/gor";
            } else if ("admin".equalsIgnoreCase(user.getRole())) {
                return "redirect:/home";
            } else {
                return "redirect:/home";
            }
        } else {
            model.addAttribute("error", "Email atau password salah!");
            return "logreg/login";
        }
    }

    @GetMapping("/user/gor")
    public String userGorPage(HttpSession session, Model model) {
        Users user = (Users) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        model.addAttribute("role", session.getAttribute("role"));
        // Ambil data GOR dari service dan kirim ke view
        model.addAttribute("gors", gorService.getAllGor());
        return "user/gor";
    }

    @GetMapping("/home")
    public String home(HttpSession session, Model model) {
        long totalUser = userService.count();
        long totalGor = gorService.count();
        long totalLapangan = lapanganService.count();
        Users user = (Users) session.getAttribute("user");
        model.addAttribute("user", user); // user bisa null, tidak masalah
        model.addAttribute("totalUser", totalUser);
        model.addAttribute("totalGor", totalGor);
        model.addAttribute("totalLapangan", totalLapangan);
        return "logreg/index";
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