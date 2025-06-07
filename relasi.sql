-- NOTE: Jika belum ada relasi pada tabel, eksekusi perintah ini untuk menambahkan relasi.
-- Relasi Tabel Gor dan Lapangan
ALTER TABLE
    `lapangan`
ADD
    FOREIGN KEY (`id_gor`) REFERENCES `gor`(`id`) ON DELETE RESTRICT ON UPDATE CASCADE;

-- Relasi Tabel Booking dengan Lapangan dan Users
ALTER TABLE
    `booking`
ADD
    FOREIGN KEY (`id_lapangan`) REFERENCES `lapangan`(`id`) ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE
    `booking`
ADD
    FOREIGN KEY (`id_user`) REFERENCES `users`(`id`) ON DELETE RESTRICT ON UPDATE CASCADE;

-- Relasi Tabel Transaksi dengan Booking, Lapangan, Promo, dan Users
ALTER TABLE
    `transaksi`
ADD
    FOREIGN KEY (`id_booking`) REFERENCES `booking`(`id`) ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE
    `transaksi`
ADD
    FOREIGN KEY (`id_lapangan`) REFERENCES `lapangan`(`id`) ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE
    `transaksi`
ADD
    FOREIGN KEY (`id_promo`) REFERENCES `promo`(`id`) ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE
    `transaksi`
ADD
    FOREIGN KEY (`id_user`) REFERENCES `users`(`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;