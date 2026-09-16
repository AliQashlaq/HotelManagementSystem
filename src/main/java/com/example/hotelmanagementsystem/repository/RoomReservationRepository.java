package com.example.hotelmanagementsystem.repository;

import com.example.hotelmanagementsystem.entity.RoomReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomReservationRepository extends JpaRepository<RoomReservation, Long> {
}
