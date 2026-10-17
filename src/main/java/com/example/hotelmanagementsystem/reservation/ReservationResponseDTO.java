package com.example.hotelmanagementsystem.reservation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

public record ReservationResponseDTO(
        Long id,
        Long userId,
        Set<Long> roomIds,
        LocalDate checkInDate,
        LocalDate checkOutDate,
        ReservationStatus status,
        BigDecimal price
) {
}
