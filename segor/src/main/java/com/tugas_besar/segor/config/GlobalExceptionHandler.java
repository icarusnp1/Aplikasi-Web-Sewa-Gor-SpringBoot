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

        String referer = request.getHeader("Referer");
        redirectAttributes.addFlashAttribute("errorMessage", "Data tidak dapat dihapus karena sedang digunakan di tabel lain.");

        return "redirect:" + (referer != null ? referer : "/");
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception ex,
                                         RedirectAttributes redirectAttributes,
                                         HttpServletRequest request) {

        String referer = request.getHeader("Referer");

        // Log semua exception umum
        System.err.println("General Exception caught:");
        ex.printStackTrace();

        redirectAttributes.addFlashAttribute("errorMessage",
                "Terjadi kesalahan internal: " + ex.getMessage());

        return "redirect:" + (referer != null ? referer : "/");
    }
}
