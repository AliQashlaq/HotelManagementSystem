package com.example.hotelmanagementsystem.room;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByRoomNumber(String roomNumber);
    Optional<Room> findByRoomNumberAndRoomType(String roomNumber, RoomType roomType);
    boolean existsByRoomNumber(String roomNumber);
}
