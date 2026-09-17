package com.example.hotelmanagementsystem.repository;

import com.example.hotelmanagementsystem.entity.Room;
import com.example.hotelmanagementsystem.entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByRoomNumber(String roomNumber);
    Optional<Room> findByRoomNumberAndRoomType(String roomNumber, RoomType roomType);
    boolean existsByRoomNumber(String roomNumber);
}
