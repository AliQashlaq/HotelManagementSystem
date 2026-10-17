package com.example.hotelmanagementsystem.roomreservation;

import java.math.BigDecimal;

public record RoomReservationResponseDTO(
        Long id,
        Long roomId,
        String roomNumber,
        BigDecimal pricePerNight
) {
}
