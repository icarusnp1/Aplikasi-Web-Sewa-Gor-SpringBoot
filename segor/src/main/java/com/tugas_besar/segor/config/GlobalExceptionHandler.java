package com.tugas_besar.segor.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleDataIntegrityViolationException(
            DataIntegrityViolationException ex,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request) {

        // Ambil URL referer (asal request)
        String referer = request.getHeader("Referer");

        // Tambahkan pesan error untuk ditampilkan di halaman selanjutnya
        redirectAttributes.addFlashAttribute("errorMessage", "Data tidak dapat dihapus karena sedang digunakan di tabel lain.");

        // Jika berasal dari halaman promo, redirect ke /promo (untuk keamanan dan konsistensi)
        if (referer != null && referer.contains("/promo")) {
            return "redirect:/promo";
        }

        // Jika dari halaman lain, redirect kembali ke referer
        return "redirect:" + (referer != null ? referer : "/");
    }
}
    