package com.tugas_besar.segor.repository;

import com.tugas_besar.segor.entity.GorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GorRepository extends JpaRepository<GorEntity, Integer> {
    
}