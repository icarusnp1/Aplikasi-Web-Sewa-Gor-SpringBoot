package com.tugas_besar.segor.controllers;

import com.tugas_besar.segor.entity.BookingEntity;
import com.tugas_besar.segor.entity.LapanganEntity;
import com.tugas_besar.segor.entity.Users;
import com.tugas_besar.segor.service.BookingService;
import com.tugas_besar.segor.service.LapanganService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;
    
    @Autowired
    private LapanganService lapanganService;

    // Show all bookings
    @GetMapping({ "/list", "" })
    public String getAllBooking(Model model) {
        List<BookingEntity> bookings = bookingService.getAllBooking();
        model.addAttribute("bookings", bookings);
        return "booking/booking";
    }

    // Show create form
    @GetMapping("/create")
    public String showCreateForm(Model model, HttpSession session) {
        Users user = (Users) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("userId", user.getId());
        }
        model.addAttribute("booking", new BookingEntity());

        // Get users and lapangan for dropdowns
        List<Users> users = bookingService.getAllUsers();
        List<LapanganEntity> lapangans = bookingService.getAllLapangan();

        model.addAttribute("users", users);
        model.addAttribute("lapangans", lapangans);

        return "booking/create";
    }

    // Show create form
    @GetMapping("/create/{id_lapangan}")
    public String showCreateForm(Model model, HttpSession session, @PathVariable Integer id_lapangan) {
        Users user = (Users) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("userId", user.getId());
        }
        model.addAttribute("booking", new BookingEntity());

        // Get lapangan
        LapanganEntity lapangan = lapanganService.getLapanganById(id_lapangan).orElse(null);

        model.addAttribute("lapangans", lapangan);

        return "booking/create";
    }

    // Show create form
    @GetMapping("/user/create/{id_lapangan}")
    public String showUserCreateForm(Model model, HttpSession session, @PathVariable Integer id_lapangan) {
        Users user = (Users) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("userId", user.getId());
        }
        model.addAttribute("booking", new BookingEntity());

        // Get lapangan
        LapanganEntity lapangan = lapanganService.getLapanganById(id_lapangan).orElse(null);

        model.addAttribute("lapangan", lapangan);

        return "user/booking";
    }

    // Handle create form
    @PostMapping("/save")
    public String createBooking(@ModelAttribute("booking") BookingEntity booking,
            RedirectAttributes redirectAttributes, HttpSession session) {
        try {
            Users user = (Users) session.getAttribute("user");
            if (user != null) {
                booking.setUser(user);
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Anda harus login untuk membuat booking!");
                return "redirect:/booking/create";
            }
            bookingService.createBooking(booking);
            redirectAttributes.addFlashAttribute("successMessage", "Booking berhasil dibuat!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/booking/create";
        }
        return "redirect:/booking/list";
    }

    @PostMapping("/user/save")
    public String createUserBooking(@ModelAttribute("booking") BookingEntity booking,
            RedirectAttributes redirectAttributes, HttpSession session) {
        try {
            Users user = (Users) session.getAttribute("user");
            if (user != null) {
                booking.setUser(user);
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Anda harus login untuk membuat booking!");
                return "redirect:/booking/user/create";
            }
            bookingService.createBooking(booking);
            redirectAttributes.addFlashAttribute("successMessage", "Booking berhasil dibuat!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/booking/user/create";
        }
        return "redirect:/lapangan/user/list/gor/" + booking.getLapangan().getGor().getId();
    }

    // Show edit form
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        Optional<BookingEntity> booking = bookingService.getBookingById(id);
        if (booking.isPresent()) {
            model.addAttribute("booking", booking.get());

            // Get users and lapangan for dropdowns
            List<Users> users = bookingService.getAllUsers();
            List<LapanganEntity> lapangans = bookingService.getAllLapangan();

            model.addAttribute("users", users);
            model.addAttribute("lapangans", lapangans);

            return "booking/edit";
        } else {
            return "redirect:/booking/list";
        }
    }

    // Handle update form
    @PostMapping("/update/{id}")
    public String updateBooking(@PathVariable Integer id,
            @ModelAttribute("booking") BookingEntity bookingDetails,
            RedirectAttributes redirectAttributes) {
        try {
            bookingService.updateBooking(id, bookingDetails);
            redirectAttributes.addFlashAttribute("successMessage", "Booking berhasil diupdate!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/booking/edit/" + id;
        }
        return "redirect:/booking/list";
    }

    // Handle delete
    @PostMapping("/delete/{id}")
    public String deleteBooking(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        if (bookingService.deleteBooking(id)) {
            redirectAttributes.addFlashAttribute("successMessage", "Booking berhasil dihapus!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Booking tidak ditemukan!");
        }
        return "redirect:/booking/list";
    }

    // Show bookings by user
    @GetMapping("/user/{userId}")
    public String getBookingsByUser(@PathVariable Integer userId, Model model) {
        List<BookingEntity> bookings = bookingService.getBookingsByUser(userId);
        model.addAttribute("bookings", bookings);
        model.addAttribute("filterType", "User ID: " + userId);
        return "booking/booking";
    }

    // Show bookings by lapangan
    @GetMapping("/lapangan/{lapanganId}")
    public String getBookingsByLapangan(@PathVariable Integer lapanganId, Model model) {
        List<BookingEntity> bookings = bookingService.getBookingsByLapangan(lapanganId);
        model.addAttribute("bookings", bookings);
        model.addAttribute("filterType", "Lapangan ID: " + lapanganId);
        return "booking/booking";
    }
}