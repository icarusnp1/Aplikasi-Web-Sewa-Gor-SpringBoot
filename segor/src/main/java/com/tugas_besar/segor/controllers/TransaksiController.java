package com.tugas_besar.segor.controllers;

import com.tugas_besar.segor.entity.TransaksiEntity;
import com.tugas_besar.segor.entity.Users;
import com.tugas_besar.segor.service.TransaksiService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class TransaksiController {

    @Autowired
    private TransaksiService transaksiService;

    @GetMapping("/user/transaksi")
    public String transaksiUserPage(HttpSession session, Model model) {
        Users user = (Users) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        List<TransaksiEntity> transaksiList = transaksiService.getTransaksiByUser(user);
        model.addAttribute("transaksis", transaksiList);
        model.addAttribute("user", user); // agar bisa dipakai di navbar_user
        return "user/transaksi";
    }
}