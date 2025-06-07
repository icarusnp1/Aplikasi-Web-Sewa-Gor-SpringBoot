package com.tugas_besar.segor.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "promo")
public class PromoEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "total_promo", nullable = false)
    private Integer totalPromo;
    
    // Default constructor
    public PromoEntity() {}
    
    // Constructor with parameters
    public PromoEntity(Integer totalPromo) {
        this.totalPromo = totalPromo;
    }
    
    // Getters and Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getTotalPromo() {
        return totalPromo;
    }
    
    public void setTotalPromo(Integer totalPromo) {
        this.totalPromo = totalPromo;
    }
    
    @Override
    public String toString() {
        return "PromoEntity{" +
                "id=" + id +
                ", totalPromo=" + totalPromo +
                '}';
    }
}