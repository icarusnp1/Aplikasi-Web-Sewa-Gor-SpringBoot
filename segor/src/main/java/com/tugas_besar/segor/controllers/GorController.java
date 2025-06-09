package com.tugas_besar.segor.controllers;

import com.tugas_besar.segor.SegorApplication;
import com.tugas_besar.segor.entity.GorEntity;
import com.tugas_besar.segor.service.GorService;
import com.tugas_besar.segor.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/gor")
public class GorController {

    private final UserService userService;
    private final GorService gorService;
    private final SegorApplication segorApplication;

    @Autowired
    public GorController(SegorApplication segorApplication, UserService userService, GorService gorService) {
        this.segorApplication = segorApplication;
        this.userService = userService;
        this.gorService = gorService;
    }

    // Show all GORs
    @GetMapping({ "/list", "" })
    public String getAllGor(Model model, HttpSession session) {
        List<GorEntity> gors = gorService.getAllGor();
        model.addAttribute("gors", gors);
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("role", session.getAttribute("role"));
        return "gor/gor";
    }

    // Show create form
    @GetMapping("/create")
    public String showCreateForm(Model model, HttpSession session) {
        model.addAttribute("gor", new GorEntity());
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("role", session.getAttribute("role"));
        return "gor/create";
    }

    // Handle create form
    @PostMapping("/save")
    public String createGor(@ModelAttribute("gor") GorEntity gor,
                            @RequestParam("imgFile") MultipartFile imgFile,
                            HttpSession session) {
        if (!imgFile.isEmpty()) {
            String fileName = imgFile.getOriginalFilename();
            if (fileName != null
                    && (fileName.endsWith(".png") || fileName.endsWith(".jpg") || fileName.endsWith(".jpeg"))) {
                try {
                    gor.setImg(imgFile.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        gorService.createGor(gor);
        return "redirect:/gor/list";
    }

    @GetMapping("/user/gor")
    public String userGorPage(Model model, HttpSession session) {
        List<GorEntity> gors = gorService.getAllGor();
        model.addAttribute("gors", gors);
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("role", session.getAttribute("role"));
        return "user/gor";
    }

    // Show edit form
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model, HttpSession session) {
        Optional<GorEntity> gor = gorService.getGorById(id);
        if (gor.isPresent()) {
            model.addAttribute("gor", gor.get());
            model.addAttribute("user", session.getAttribute("user"));
            model.addAttribute("role", session.getAttribute("role"));
            return "gor/edit";
        } else {
            return "redirect:/gor/list";
        }
    }

    // Handle update form
    @PostMapping("/update/{id}")
    public String updateGor(
            @PathVariable Integer id,
            @ModelAttribute("gor") GorEntity gorDetails,
            @RequestParam(value = "imgFile", required = false) MultipartFile imgFile) {
        try {
            if (!imgFile.isEmpty()) {
                gorDetails.setImg(imgFile.getBytes());
            } else {
                GorEntity existing = gorService.getGorById(id).orElse(null);
                if (existing != null) {
                    gorDetails.setImg(existing.getImg());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        gorService.updateGor(id, gorDetails);
        return "redirect:/gor/list";
    }

    // Handle delete
    @PostMapping("/delete/{id}")
    public String deleteGor(@PathVariable Integer id) {
        gorService.deleteGor(id);
        return "redirect:/gor/list";
    }

    // Serve image
    @GetMapping("/img/{id}")
    public ResponseEntity<byte[]> getGorImage(@PathVariable Integer id) {
        Optional<GorEntity> gor = gorService.getGorById(id);
        if (gor.isPresent()) {
            byte[] img = gor.get().getImg();
            if (img != null && img.length > 0) {
                return ResponseEntity.ok()
                    .header("Content-Type", "image/jpeg")
                    .body(img);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

// ...existing code...

    @GetMapping("/user/gor/search")
    public String searchGor(@RequestParam("keyword") String keyword, Model model, HttpSession session) {
        List<GorEntity> gors = gorService.searchByNama(keyword);
        model.addAttribute("gors", gors);
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("role", session.getAttribute("role"));
        return "user/gor";
    }

    // ...existing code...
}