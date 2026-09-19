package com.example.hotelmanagementsystem.repository;

import com.example.hotelmanagementsystem.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Set;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("""
    SELECT COUNT(rr) > 0 FROM RoomReservation rr
    WHERE rr.room.roomId IN :roomIds
      AND rr.reservation.checkInDate < :checkOutDate
      AND rr.reservation.checkOutDate > :checkInDate
    """)
    boolean existsOverlappingReservations(
            @Param("roomIds") Set<Long> roomIds,
            @Param("checkInDate") LocalDate checkInDate,
            @Param("checkOutDate") LocalDate checkOutDate
    );
}
