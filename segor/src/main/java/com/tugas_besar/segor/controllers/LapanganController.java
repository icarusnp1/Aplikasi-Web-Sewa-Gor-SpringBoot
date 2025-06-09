package com.tugas_besar.segor.controllers;

import com.tugas_besar.segor.entity.LapanganEntity;
import com.tugas_besar.segor.entity.BookingEntity;
import com.tugas_besar.segor.entity.GorEntity;
import com.tugas_besar.segor.service.LapanganService;
import com.tugas_besar.segor.service.GorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/lapangan")
public class LapanganController {

    @Autowired
    private LapanganService lapanganService;

    @Autowired
    private GorService gorService;

    @GetMapping({ "/list", "" })
    public String getAllLapangan(Model model) {
        List<LapanganEntity> lapangans = lapanganService.getAllLapangan();
        model.addAttribute("lapangans", lapangans);
        return "lapangan/lapangan";
    }

    @GetMapping("/list/gor/{id_gor}")
    public String getLapanganByGor(@PathVariable("id_gor") Integer idGor, Model model) {
        List<LapanganEntity> lapangans = lapanganService.getLapanganByGorId(idGor);
        GorEntity gor = gorService.getGorById(idGor).orElse(null);
        if (gor != null) {
            model.addAttribute("gor", gor);
        } else {
            model.addAttribute("error", "Gor not found");
        }
        model.addAttribute("lapangans", lapangans);
        model.addAttribute("id_gor", idGor);
        return "lapangan/lapangan";
    }

    @GetMapping("/user/list/gor/{id_gor}")
    public String getUserLapanganByGor(@PathVariable("id_gor") Integer idGor, Model model) {
        List<LapanganEntity> lapangans = lapanganService.getLapanganByGorId(idGor);
        model.addAttribute("lapangans", lapangans);
        model.addAttribute("id_gor", idGor);
        return "user/lapangan";
    }

    @GetMapping("/create")
    public String showCreateForm(@RequestParam("id_gor") Integer idGor, Model model) {
        LapanganEntity lapangan = new LapanganEntity();
        GorEntity gor = gorService.getGorById(idGor).orElse(null);
        lapangan.setGor(gor);
        model.addAttribute("lapangan", lapangan);
        model.addAttribute("id_gor", idGor);
        return "lapangan/create";
    }

    @PostMapping("/save")
    public String createLapangan(@ModelAttribute("lapangan") LapanganEntity lapangan) {
        // Fetch the managed GorEntity from DB using the id from the form
        Integer gorId = lapangan.getGor().getId();
        GorEntity gor = gorService.getGorById(gorId).orElse(null);
        lapangan.setGor(gor);

        lapanganService.createLapangan(lapangan);
        return "redirect:/lapangan/list/gor/" + gorId;
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        Optional<LapanganEntity> lapangan = lapanganService.getLapanganById(id);
        if (lapangan.isPresent()) {
            model.addAttribute("lapangan", lapangan.get());
            model.addAttribute("gors", gorService.getAllGor());
            return "lapangan/edit";
        } else {
            return "redirect:/lapangan/list";
        }
    }

    @PostMapping("/update/{id}")
    public String updateLapangan(@PathVariable Integer id, @ModelAttribute("lapangan") LapanganEntity lapanganDetails) {
        lapanganService.updateLapangan(id, lapanganDetails);
        return "redirect:/lapangan/list";
    }

    @PostMapping("/delete/{id}")
    public String deleteLapangan(@PathVariable Integer id) {
        lapanganService.deleteLapangan(id);
        return "redirect:/lapangan/list";
    }
}