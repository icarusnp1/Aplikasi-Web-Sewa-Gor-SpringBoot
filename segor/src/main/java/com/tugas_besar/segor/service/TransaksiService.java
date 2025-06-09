package com.tugas_besar.segor.service;

import com.tugas_besar.segor.entity.TransaksiEntity;
import com.tugas_besar.segor.entity.Users;
import com.tugas_besar.segor.repository.TransaksiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransaksiService {
    @Autowired
    private TransaksiRepository transaksiRepository;

    public List<TransaksiEntity> getTransaksiByUser(Users user) {
        return transaksiRepository.findByUser(user);
    }

    public TransaksiEntity saveTransaksi(TransaksiEntity transaksi) {
        return transaksiRepository.save(transaksi);
    }
}