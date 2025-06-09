package com.tugas_besar.segor.service;

import com.tugas_besar.segor.entity.GorEntity;
import com.tugas_besar.segor.repository.GorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GorService {

    @Autowired
    private GorRepository gorRepository;

    public List<GorEntity> getAllGor() {
        return gorRepository.findAll();
    }

    public Optional<GorEntity> getGorById(Integer id) {
        return gorRepository.findById(id);
    }

    public GorEntity createGor(GorEntity gor) {
        return gorRepository.save(gor);
    }

    public GorEntity updateGor(Integer id, GorEntity gorDetails) {
        GorEntity gor = gorRepository.findById(id).orElseThrow();
        gor.setNama(gorDetails.getNama());
        gor.setAlamat(gorDetails.getAlamat());
        gor.setImg(gorDetails.getImg());
        return gorRepository.save(gor);
    }

    public void deleteGor(Integer id) {
        gorRepository.deleteById(id);
    }

    // Menghitung total gor di database
    public long count() {
        return gorRepository.count();
    }


    public List<GorEntity> searchByNama(String nama) {
        return gorRepository.findByNamaContainingIgnoreCase(nama);
    }
}