package com.tugas_besar.segor.controllers;

import com.tugas_besar.segor.entity.GorEntity;
import com.tugas_besar.segor.service.GorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/gor")
public class GorController {

    @Autowired
    private GorService gorService;

    // Show all GORs
    @GetMapping({ "/list", "" })
    public String getAllGor(Model model) {
        List<GorEntity> gors = gorService.getAllGor();
        model.addAttribute("gors", gors);
        return "gor/gor";
    }

    // Show create form
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("gor", new GorEntity());
        return "gor/create";
    }

    // Handle create form
    @PostMapping("/save")
    public String createGor(@ModelAttribute("gor") GorEntity gor,
            @RequestParam("imgFile") MultipartFile imgFile) {
        if (!imgFile.isEmpty()) {
            String fileName = imgFile.getOriginalFilename();
            if (fileName != null
                    && (fileName.endsWith(".png") || fileName.endsWith(".jpg") || fileName.endsWith(".jpeg"))) {
                try {
                    // Save to src/main/resources/static/images/
                    String uploadDir = "src/main/resources/static/images/";
                    File dir = new File(uploadDir);
                    if (!dir.exists())
                        dir.mkdirs();
                    Path filePath = Paths.get(uploadDir, fileName);
                    Files.write(filePath, imgFile.getBytes());
                    gor.setImg("/images/" + fileName); // Save relative path
                } catch (IOException e) {
                    e.printStackTrace();
                    // Optionally, add error handling here
                }
            } else {
                // Optionally, add error handling for invalid file type
            }
        }
        gorService.createGor(gor);
        return "redirect:/gor/list";
    }

    // Show edit form
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        Optional<GorEntity> gor = gorService.getGorById(id);
        if (gor.isPresent()) {
            model.addAttribute("gor", gor.get());
            return "gor/edit";
        } else {
            return "redirect:/gor/list";
        }
    }

    // Handle update form
    @PostMapping("/update/{id}")
    public String updateGor(@PathVariable Integer id, @ModelAttribute("gor") GorEntity gorDetails) {
        gorService.updateGor(id, gorDetails);
        return "redirect:/gor/list";
    }

    // Handle delete
    @PostMapping("/delete/{id}")
    public String deleteGor(@PathVariable Integer id) {
        gorService.deleteGor(id);
        return "redirect:/gor/list";
    }
}