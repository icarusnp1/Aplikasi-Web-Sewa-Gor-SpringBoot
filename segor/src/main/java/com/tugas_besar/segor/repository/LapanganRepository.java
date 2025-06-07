package com.tugas_besar.segor.repository;

import com.tugas_besar.segor.entity.LapanganEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LapanganRepository extends JpaRepository<LapanganEntity, Integer> {
    List<LapanganEntity> findByGor_Id(Integer idGor);
}