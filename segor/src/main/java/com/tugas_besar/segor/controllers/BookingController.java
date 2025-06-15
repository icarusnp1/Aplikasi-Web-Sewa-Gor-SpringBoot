package com.tugas_besar.segor.controllers;

import com.tugas_besar.segor.entity.BookingEntity;
import com.tugas_besar.segor.entity.GorEntity;
import com.tugas_besar.segor.entity.LapanganEntity;
import com.tugas_besar.segor.entity.PromoEntity;
import com.tugas_besar.segor.entity.TransaksiEntity;
import com.tugas_besar.segor.entity.Users;
import com.tugas_besar.segor.service.BookingService;
import com.tugas_besar.segor.service.LapanganService;
import com.tugas_besar.segor.service.TransaksiService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private LapanganService lapanganService;

    @Autowired
    private TransaksiService transaksiService;

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

        List<Users> users = bookingService.getAllUsers();
        List<LapanganEntity> lapangans = bookingService.getAllLapangan();

        model.addAttribute("users", users);
        model.addAttribute("lapangans", lapangans);

        return "booking/create";
    }

    // Show create form by lapangan
    @GetMapping("/create/{id_lapangan}")
    public String showCreateForm(Model model, HttpSession session, @PathVariable("id_lapangan") Integer id_lapangan) {
        Users user = (Users) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("userId", user.getId());
        }
        model.addAttribute("booking", new BookingEntity());

        LapanganEntity lapangan = lapanganService.getLapanganById(id_lapangan).orElse(null);
        model.addAttribute("lapangans", lapangan);

        GorEntity gor = lapangan.getGor();
        model.addAttribute("id_gor", gor.getId());

        return "booking/create";
    }

    // Show user create form
    @GetMapping("/user/create/{id_lapangan}")
    public String showUserCreateForm(Model model, HttpSession session,
            @PathVariable("id_lapangan") Integer id_lapangan,
            @RequestParam(value = "filterDate", required = false) String filterDateStr) {
        Users user = (Users) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("userId", user.getId());
        }
        model.addAttribute("booking", new BookingEntity());

        LapanganEntity lapangan = lapanganService.getLapanganById(id_lapangan).orElse(null);
        model.addAttribute("lapangan", lapangan);

        List<BookingEntity> bookedList;
        if (filterDateStr != null && !filterDateStr.isEmpty()) {
            LocalDate filterDate = LocalDate.parse(filterDateStr);
            bookedList = bookingService.getBookingsByLapanganAndDate(id_lapangan, filterDate);
            model.addAttribute("filterDate", filterDateStr);
        } else {
            bookedList = bookingService.getBookingsByLapangan(id_lapangan);
        }
        model.addAttribute("bookedList", bookedList);

        return "user/booking";
    }

    // Handle create booking user
    @PostMapping("/user/save")
    public String createUserBooking(
            @ModelAttribute("booking") BookingEntity booking,
            @RequestParam(value = "promoId", required = false) Integer promoId,
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

            TransaksiEntity transaksi = new TransaksiEntity();
            transaksi.setUser(user);
            transaksi.setLapangan(booking.getLapangan());
            transaksi.setBooking(booking);
            transaksi.setGor(booking.getLapangan().getGor());
            transaksi.setStatus("terbayar");
            transaksi.setTanggal(LocalDateTime.now());

            // Set promo jika ada
            if (promoId != null) {
                PromoEntity promo = new PromoEntity();
                promo.setId(promoId);
                transaksi.setPromo(promo);
            } else {
                transaksi.setPromo(null);
            }

            if (booking.getJamMulai() != null && booking.getJamSelesai() != null) {
                int durasi = booking.getJamSelesai().toSecondOfDay() - booking.getJamMulai().toSecondOfDay();
                transaksi.setDurasi(durasi / 3600);
            }

            transaksiService.saveTransaksi(transaksi);

            redirectAttributes.addFlashAttribute("successMessage", "Booking berhasil dibuat!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/booking/user/create/" + booking.getLapangan().getId();
        }
        return "redirect:/booking/user/create/" + booking.getLapangan().getId();
    }

    // Handle create booking (admin/general)
    @PostMapping("/save")
    public String createBooking(
            @ModelAttribute("booking") BookingEntity booking,
            @RequestParam(value = "promoId", required = false) Integer promoId,
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

            TransaksiEntity transaksi = new TransaksiEntity();
            transaksi.setUser(user);
            transaksi.setLapangan(booking.getLapangan());
            transaksi.setBooking(booking);
            transaksi.setGor(booking.getLapangan().getGor());
            transaksi.setStatus("terbayar");
            transaksi.setTanggal(LocalDateTime.now());

            // Set promo jika ada
            if (promoId != null) {
                PromoEntity promo = new PromoEntity();
                promo.setId(promoId);
                transaksi.setPromo(promo);
            } else {
                transaksi.setPromo(null);
            }

            if (booking.getJamMulai() != null && booking.getJamSelesai() != null) {
                int durasi = booking.getJamSelesai().toSecondOfDay() - booking.getJamMulai().toSecondOfDay();
                transaksi.setDurasi(durasi / 3600);
            }

            transaksiService.saveTransaksi(transaksi);

            redirectAttributes.addFlashAttribute("successMessage", "Booking berhasil dibuat!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/booking/create/" + booking.getLapangan().getId();
        }
        return "redirect:/booking/create/" + booking.getLapangan().getId();
    }

    // Show edit form
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model) {
        Optional<BookingEntity> booking = bookingService.getBookingById(id);
        if (booking.isPresent()) {
            model.addAttribute("booking", booking.get());

            List<Users> users = bookingService.getAllUsers();
            List<LapanganEntity> lapangans = bookingService.getAllLapangan();

            model.addAttribute("users", users);
            model.addAttribute("lapangans", lapangans);

            return "booking/edit";
        } else {
            return "redirect:/booking/list";
        }
    }

    // Handle update booking
    @PostMapping("/update/{id}")
    public String updateBooking(
        @PathVariable("id") Integer id,
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
}