package com.tugas_besar.segor.repository;

import com.tugas_besar.segor.entity.TransaksiEntity;
import com.tugas_besar.segor.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TransaksiRepository extends JpaRepository<TransaksiEntity, Integer> {
    List<TransaksiEntity> findByUser(Users user);
}