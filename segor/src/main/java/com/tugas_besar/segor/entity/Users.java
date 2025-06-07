package com.tugas_besar.segor.entity;

import jakarta.persistence.*;

@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nama;
    private String password;
    private String role;

    // getter-setter
    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
