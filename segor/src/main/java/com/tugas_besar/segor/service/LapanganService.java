package com.tugas_besar.segor.service;

import com.tugas_besar.segor.entity.LapanganEntity;
import com.tugas_besar.segor.repository.LapanganRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LapanganService {

    @Autowired
    private LapanganRepository lapanganRepository;

    public List<LapanganEntity> getAllLapangan() {
        return lapanganRepository.findAll();
    }

    public List<LapanganEntity> getLapanganByGorId(Integer idGor) {
        return lapanganRepository.findByGor_Id(idGor);
    }

    public Optional<LapanganEntity> getLapanganById(Integer id) {
        return lapanganRepository.findById(id);
    }

    public LapanganEntity createLapangan(LapanganEntity lapangan) {
        return lapanganRepository.save(lapangan);
    }

    public LapanganEntity updateLapangan(Integer id, LapanganEntity lapanganDetails) {
        LapanganEntity lapangan = lapanganRepository.findById(id).orElseThrow();
        lapangan.setGor(lapanganDetails.getGor());
        lapangan.setJenis(lapanganDetails.getJenis());
        lapangan.setTarif(lapanganDetails.getTarif());
        lapangan.setStatus(lapanganDetails.getStatus());
        lapangan.setDeskripsi(lapanganDetails.getDeskripsi());
        return lapanganRepository.save(lapangan);
    }

    public void deleteLapangan(Integer id) {
        lapanganRepository.deleteById(id);
    }
}