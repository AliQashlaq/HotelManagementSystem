package com.example.hotelmanagementsystem.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record ReservationResponseDTO(
        Long reservationId,
        Long userId,
        String username,
        LocalDate checkInDate,
        LocalDate checkOutDate,
        List<RoomReservationResponseDTO> bookedRooms,
        double totalPrice,
        LocalDateTime createdAt
) {
}
