package com.example.hotelmanagementsystem.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record ReservationRequestDTO(
        @NotNull(message = "User ID is required")
        Long userId,

        @NotEmpty(message = "At least one room should be selected")
        List<Long> roomIds,

        @NotNull(message = "Check-in date is required")
        @FutureOrPresent(message = "Check-in date must be today or in the future")
        LocalDate checkInDate,

        @NotNull(message = "Check-out day is required")
        @Future(message = "Check-out date must be in the future")
        LocalDate checkOutDate) {
}
