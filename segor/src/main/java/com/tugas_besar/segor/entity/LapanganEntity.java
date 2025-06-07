package com.tugas_besar.segor.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "lapangan")
public class LapanganEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_gor", nullable = false)
    private GorEntity gor;

    @Column(length = 255, nullable = false)
    private String jenis;

    private Integer tarif;

    @Column(length = 255)
    private String status;

    @Column(length = 255)
    private String deskripsi;

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public GorEntity getGor() { return gor; }
    public void setGor(GorEntity gor) { this.gor = gor; }

    public String getJenis() { return jenis; }
    public void setJenis(String jenis) { this.jenis = jenis; }

    public Integer getTarif() { return tarif; }
    public void setTarif(Integer tarif) { this.tarif = tarif; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getDeskripsi() { return deskripsi; }
    public void setDeskripsi(String deskripsi) { this.deskripsi = deskripsi; }
}