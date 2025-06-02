package com.tugas_besar.segor.repository;

import com.tugas_besar.segor.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByNama(String nama);
}
