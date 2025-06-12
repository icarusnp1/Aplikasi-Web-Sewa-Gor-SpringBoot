package com.tugas_besar.segor.service;

import com.tugas_besar.segor.entity.PromoEntity;
import com.tugas_besar.segor.repository.PromoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PromoService {
    
    @Autowired
    private PromoRepository promoRepository;
    
    // Get all promos
    public List<PromoEntity> getAllPromo() {
        return promoRepository.findAll();
    }
    
    // Get promo by ID
    public Optional<PromoEntity> getPromoById(Integer id) {
        return promoRepository.findById(id);
    }
    
    // Create new promo
    public PromoEntity createPromo(PromoEntity promo) {
        return promoRepository.save(promo);
    }
    
    // Update promo
    public PromoEntity updatePromo(Integer id, PromoEntity promoDetails) {
        Optional<PromoEntity> existingPromo = promoRepository.findById(id);
        if (existingPromo.isPresent()) {
            PromoEntity promo = existingPromo.get();
            promo.setTotalPromo(promoDetails.getTotalPromo());
            return promoRepository.save(promo);
        }
        return null;
    }
    
    // Delete promo
    public boolean deletePromo(Integer id) {
        if (promoRepository.existsById(id)) {
            promoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean existsByKode(String kode) {
        if (promoRepository.existsByKode(kode)) {
            return true;
        } else {
            return false;
        }
    }
}