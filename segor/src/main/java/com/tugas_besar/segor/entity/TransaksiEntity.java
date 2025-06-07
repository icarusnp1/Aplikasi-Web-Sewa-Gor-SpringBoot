package com.tugas_besar.segor.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaksi")
public class TransaksiEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Relasi ke Users
    @ManyToOne
    @JoinColumn(name = "id_user")
    private Users user;

    // Relasi ke GOR
    @ManyToOne
    @JoinColumn(name = "gor_id")
    private GorEntity gor;

    // Relasi ke Lapangan
    @ManyToOne
    @JoinColumn(name = "id_lapangan")
    private LapanganEntity lapangan;

    // Relasi ke Booking
    @ManyToOne
    @JoinColumn(name = "id_booking")
    private BookingEntity booking;

    // Relasi ke Promo (jika ada entity Promo)
    @ManyToOne
    @JoinColumn(name = "id_promo")
    private PromoEntity promo;

    private int durasi;
    private String status;

    private LocalDateTime tanggal;

    // Getter & Setter
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Users getUser() { return user; }
    public void setUser(Users user) { this.user = user; }

    public GorEntity getGor() { return gor; }
    public void setGor(GorEntity gor) { this.gor = gor; }

    public LapanganEntity getLapangan() { return lapangan; }
    public void setLapangan(LapanganEntity lapangan) { this.lapangan = lapangan; }

    public BookingEntity getBooking() { return booking; }
    public void setBooking(BookingEntity booking) { this.booking = booking; }

    public PromoEntity getPromo() { return promo; }
    public void setPromo(PromoEntity promo) { this.promo = promo; }

    public int getDurasi() { return durasi; }
    public void setDurasi(int durasi) { this.durasi = durasi; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getTanggal() { return tanggal; }
    public void setTanggal(LocalDateTime tanggal) { this.tanggal = tanggal; }
}