package com.tugas_besar.segor.repository;

import com.tugas_besar.segor.entity.PromoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromoRepository extends JpaRepository<PromoEntity, Integer> {
    // You can add custom query methods here if needed
    // For example:
    // List<PromoEntity> findByTotalPromoGreaterThan(Integer totalPromo);
}