package com.tugas_besar.segor.controllers;

import com.tugas_besar.segor.entity.PromoEntity;
import com.tugas_besar.segor.entity.TransaksiEntity;
import com.tugas_besar.segor.entity.Users;
import com.tugas_besar.segor.service.TransaksiService;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TransaksiController {

    @Autowired
    private TransaksiService transaksiService;

    @ManyToOne
    @JoinColumn(name = "promo_id")
    private PromoEntity promo;

    @PostMapping("/user/cek-promo")
    public String cekPromo(@RequestParam int promoId, Model model) {
        boolean exists = transaksiService.cekPromoDiTransaksi(promoId);
        if (exists) {
            model.addAttribute("promoStatus", "Promo ditemukan di transaksi.");
        } else {
            model.addAttribute("promoStatus", "Promo tidak ditemukan.");
        }
        return "user/transaksi";
    }

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

    @GetMapping("/transaksi")
    public String transaksiPage(HttpSession session, Model model) {
        Users user = (Users) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        List<TransaksiEntity> transaksiList = transaksiService.getTransaksiByUser(user);
        model.addAttribute("transaksis", transaksiList);
        model.addAttribute("user", user); // agar bisa dipakai di navbar_user
        return "transaksi/transaksi";
    }
}