package com.tugas_besar.segor.controllers;

import com.tugas_besar.segor.entity.PromoEntity;
import com.tugas_besar.segor.repository.PromoRepository;
import com.tugas_besar.segor.service.PromoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/promo")
public class PromoController {

    private final PromoRepository promoRepository;

    @Autowired
    private PromoService promoService;

    PromoController(PromoRepository promoRepository) {
        this.promoRepository = promoRepository;
    }

    // Show all promos
    @GetMapping({ "/list", "" })
    public String getAllPromo(Model model) {
        List<PromoEntity> promos = promoService.getAllPromo();
        model.addAttribute("promos", promos);
        return "promo/promo";
    }

    // Show create form
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("promo", new PromoEntity());
        return "promo/create";
    }

    // Handle create form
    @PostMapping("/save")
    public String createPromo(@ModelAttribute("promo") PromoEntity promo, RedirectAttributes redirectAttributes) {
        if (promoService.existsByKode(promo.getKode())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Kode promo sudah ada!");
            return "redirect:/promo/create";
        }
        promoService.createPromo(promo);
        return "redirect:/promo/list";
    }


    // ...existing code...

// Show edit form
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model) {
        Optional<PromoEntity> promo = promoService.getPromoById(id);
        if (promo.isPresent()) {
            model.addAttribute("promo", promo.get());
            return "promo/edit";
        } else {
            return "redirect:/promo/list";
        }
    }

// Handle update form
    @PostMapping("/update/{id}")
    public String updatePromo(@PathVariable("id") Integer id, @ModelAttribute("promo") PromoEntity promoDetails) {
        promoService.updatePromo(id, promoDetails);
        return "redirect:/promo/list";
    }

// Handle delete
    @PostMapping("/delete/{id}")
    public String deletePromo(@PathVariable("id") Integer id) {
        promoService.deletePromo(id);
        return "redirect:/promo/list";
    }
    // Tambahkan endpoint untuk mengirimkan daftar promo ke halaman booking
    @GetMapping("/list/json")
    @ResponseBody
    public List<PromoEntity> getAllPromoJson() {
        return promoService.getAllPromo();
    }
}