package com.tugas_besar.segor.entity;

import jakarta.persistence.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "booking")
public class BookingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private Users user;

    @ManyToOne
    @JoinColumn(name = "id_lapangan", nullable = false)
    private LapanganEntity lapangan;

    @Column(name = "jam_mulai", nullable = false)
    private LocalTime jamMulai;

    @Column(name = "jam_selesai", nullable = false)
    private LocalTime jamSelesai;

    @Column(name = "tanggal", nullable = false)
    private LocalDate tanggal;

    // Default constructor
    public BookingEntity() {
    }

    // Constructor with parameters
    public BookingEntity(Users user, LapanganEntity lapangan,
            LocalTime jamMulai, LocalTime jamSelesai, LocalDate tanggal) {
        this.user = user;
        this.lapangan = lapangan;
        this.jamMulai = jamMulai;
        this.jamSelesai = jamSelesai;
        this.tanggal = tanggal;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public LapanganEntity getLapangan() {
        return lapangan;
    }

    public void setLapangan(LapanganEntity lapangan) {
        this.lapangan = lapangan;
    }

    public LocalTime getJamMulai() {
        return jamMulai;
    }

    public void setJamMulai(LocalTime jamMulai) {
        this.jamMulai = jamMulai;
    }

    public LocalTime getJamSelesai() {
        return jamSelesai;
    }

    public void setJamSelesai(LocalTime jamSelesai) {
        this.jamSelesai = jamSelesai;
    }

    public LocalDate getTanggal() {
        return tanggal;
    }

    public void setTanggal(LocalDate tanggal) {
        this.tanggal = tanggal;
    }

    @Override
    public String toString() {
        return "BookingEntity{" +
                "id=" + id +
                ", user=" + (user != null ? user.getId() : null) +
                ", lapangan=" + (lapangan != null ? lapangan.getId() : null) +
                ", jamMulai=" + jamMulai +
                ", jamSelesai=" + jamSelesai +
                ", tanggal=" + tanggal +
                '}';
    }

    /**
     * Returns true if the given hour (0-23) is within the booking's jamMulai
     * (inclusive) and jamSelesai (exclusive).
     */
    public boolean isHourBooked(int jam) {
        if (jamMulai == null || jamSelesai == null)
            return false;
        int mulai = jamMulai.getHour();
        int selesai = jamSelesai.getHour();
        // Handle booking that ends at midnight (00:00)
        if (selesai == 0 && jamMulai.isBefore(jamSelesai)) {
            selesai = 24;
        }
        // Exclusive for jamSelesai, inclusive for jamMulai
        return jam >= mulai && jam < selesai;
    }

    @Transient
    public Duration getDurasi() {
        if (jamMulai != null && jamSelesai != null) {
            return Duration.between(jamMulai, jamSelesai);
        } else {
            return Duration.ZERO;
        }
    }

    @Transient
    public long getDurasiInMinutes() {
        return getDurasi().toMinutes();
    }

    @Transient
    public long getDurasiInHours() {
        return getDurasi().toHours();
    }
}