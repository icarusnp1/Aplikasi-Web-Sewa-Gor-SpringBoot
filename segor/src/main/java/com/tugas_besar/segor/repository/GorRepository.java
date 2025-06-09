package com.tugas_besar.segor.repository;

import com.tugas_besar.segor.entity.GorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GorRepository extends JpaRepository<GorEntity, Integer> {
    List<GorEntity> findByNamaContainingIgnoreCase(String nama);
}