package com.example.hotelmanagementsystem.repository;

import com.example.hotelmanagementsystem.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
