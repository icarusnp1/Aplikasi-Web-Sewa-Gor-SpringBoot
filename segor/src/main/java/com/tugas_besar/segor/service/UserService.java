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

    public Users register(Users user) {
        return repo.save(user);
    }

    public boolean login(String nama, String password) {
        Users user = repo.findByNama(nama);
        return user != null && user.getPassword().equals(password);
    }
}
