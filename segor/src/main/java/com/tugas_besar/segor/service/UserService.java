package com.tugas_besar.segor.service;

import com.tugas_besar.segor.entity.Users;
import com.tugas_besar.segor.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    // Menghitung total user di database
    public long count() {
        return repo.count();
    }

    // Register user baru, role otomatis "user"
    public Users register(Users user) {
        user.setRole("user");
        return repo.save(user);
    }

    // Cari user berdasarkan nama dan password
    public Users findByNamaAndPassword(String nama, String password) {
        Users user = repo.findByNama(nama);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}