package com.example.hotelmanagementsystem.dto.response;

import com.example.hotelmanagementsystem.entity.RoomReservation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record ReservationResponseDTO(
        Long reservationId,
        Long userId,
        LocalDate checkInDate,
        LocalDate checkOutDate,
        List<RoomReservation> bookedRooms,
        LocalDateTime createdAt
) {
}
