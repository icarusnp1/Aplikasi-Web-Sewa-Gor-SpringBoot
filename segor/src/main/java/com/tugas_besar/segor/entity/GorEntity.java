package com.tugas_besar.segor.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "gor")
public class GorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 255, nullable = false)
    private String nama;

    @Column(length = 255, nullable = false)
    private String alamat;

    @Lob
    @Column(name = "img", columnDefinition = "MEDIUMBLOB")
    private byte[] img;

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }

    public byte[] getImg() { return img; }
    public void setImg(byte[] img) { this.img = img; }
}
