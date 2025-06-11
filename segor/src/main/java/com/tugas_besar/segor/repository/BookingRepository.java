package com.tugas_besar.segor.repository;

import com.tugas_besar.segor.entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Integer> {

       // Find bookings by user ID
       List<BookingEntity> findByUserId(Integer userId);

       // Find bookings by lapangan ID
       List<BookingEntity> findByLapanganId(Integer lapanganId);

       // Find bookings by date
       List<BookingEntity> findByTanggal(LocalDate tanggal);

       // Find bookings by date range
       List<BookingEntity> findByTanggalBetween(LocalDate startDate, LocalDate endDate);

       // Check for conflicting bookings (same lapangan, overlapping time)
       @Query("SELECT b FROM BookingEntity b WHERE b.lapangan.id = :lapanganId " +
                     "AND b.tanggal = :tanggal " +
                     "AND ((b.jamMulai <= :jamSelesai AND b.jamSelesai >= :jamMulai) " +
                     "OR (b.jamMulai >= :jamMulai AND b.jamMulai < :jamSelesai))")
       List<BookingEntity> findConflictingBookings(@Param("lapanganId") Integer lapanganId,
                     @Param("tanggal") LocalDate tanggal,
                     @Param("jamMulai") LocalTime jamMulai,
                     @Param("jamSelesai") LocalTime jamSelesai);

       // Find by lapangan id and tanggal, ordered by jamMulai
       @Query("SELECT b FROM BookingEntity b WHERE b.lapangan.id = :lapanganId AND b.tanggal = :tanggal ORDER BY b.tanggal ASC, b.jamMulai ASC")
       List<BookingEntity> findByLapanganIdAndTanggal(@Param("lapanganId") Integer lapanganId,
                     @Param("tanggal") LocalDate tanggal);
}