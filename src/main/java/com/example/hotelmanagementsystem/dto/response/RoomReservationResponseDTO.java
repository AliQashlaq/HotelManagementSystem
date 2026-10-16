package com.example.hotelmanagementsystem.dto.response;

import java.math.BigDecimal;

public record RoomReservationResponseDTO(
        Long id,
        Long roomId,
        String roomNumber,
        BigDecimal pricePerNight
) {
}
