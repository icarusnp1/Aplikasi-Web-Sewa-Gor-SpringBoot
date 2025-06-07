package com.tugas_besar.segor.service;

import com.tugas_besar.segor.entity.BookingEntity;
import com.tugas_besar.segor.entity.LapanganEntity;
import com.tugas_besar.segor.entity.Users;
import com.tugas_besar.segor.repository.BookingRepository;
import com.tugas_besar.segor.repository.LapanganRepository;
import com.tugas_besar.segor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    
    @Autowired
    private BookingRepository bookingRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private LapanganRepository lapanganRepository;
    
    // Get all bookings
    public List<BookingEntity> getAllBooking() {
        return bookingRepository.findAll();
    }
    
    // Get booking by ID
    public Optional<BookingEntity> getBookingById(Integer id) {
        return bookingRepository.findById(id);
    }
    
    // Get all users for dropdown
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }
    
    // Get all lapangan for dropdown
    public List<LapanganEntity> getAllLapangan() {
        return lapanganRepository.findAll();
    }
    
    // Create new booking
    public BookingEntity createBooking(BookingEntity booking) {
        // Validate if booking conflicts with existing bookings
        List<BookingEntity> conflicts = bookingRepository.findConflictingBookings(
            booking.getLapangan().getId(),
            booking.getTanggal(),
            booking.getJamMulai(),
            booking.getJamSelesai()
        );
        
        if (!conflicts.isEmpty()) {
            throw new RuntimeException("Jadwal bertabrakan dengan booking yang sudah ada!");
        }
        
        return bookingRepository.save(booking);
    }
    
    // Update booking
    public BookingEntity updateBooking(Integer id, BookingEntity bookingDetails) {
        Optional<BookingEntity> existingBooking = bookingRepository.findById(id);
        if (existingBooking.isPresent()) {
            BookingEntity booking = existingBooking.get();
            
            // Check for conflicts excluding current booking
            List<BookingEntity> conflicts = bookingRepository.findConflictingBookings(
                bookingDetails.getLapangan().getId(),
                bookingDetails.getTanggal(),
                bookingDetails.getJamMulai(),
                bookingDetails.getJamSelesai()
            );
            
            // Remove current booking from conflicts
            conflicts.removeIf(b -> b.getId().equals(id));
            
            if (!conflicts.isEmpty()) {
                throw new RuntimeException("Jadwal bertabrakan dengan booking yang sudah ada!");
            }
            
            booking.setUser(bookingDetails.getUser());
            booking.setLapangan(bookingDetails.getLapangan());
            booking.setJamMulai(bookingDetails.getJamMulai());
            booking.setJamSelesai(bookingDetails.getJamSelesai());
            booking.setTanggal(bookingDetails.getTanggal());
            
            return bookingRepository.save(booking);
        }
        return null;
    }
    
    // Delete booking
    public boolean deleteBooking(Integer id) {
        if (bookingRepository.existsById(id)) {
            bookingRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    // Get bookings by user
    public List<BookingEntity> getBookingsByUser(Integer userId) {
        return bookingRepository.findByUserId(userId);
    }
    
    // Get bookings by lapangan
    public List<BookingEntity> getBookingsByLapangan(Integer lapanganId) {
        return bookingRepository.findByLapanganId(lapanganId);
    }
    
    // Get bookings by date
    public List<BookingEntity> getBookingsByDate(LocalDate tanggal) {
        return bookingRepository.findByTanggal(tanggal);
    }
}