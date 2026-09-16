package com.example.hotelmanagementsystem.dto.response;

public record RoomReservationResponseDTO(
        Long id,
        Long roomId,
        String roomNumber,
        double pricePerNight
) {
}
