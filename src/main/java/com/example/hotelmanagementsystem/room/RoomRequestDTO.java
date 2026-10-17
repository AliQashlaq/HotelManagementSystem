package com.example.hotelmanagementsystem.room;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record RoomRequestDTO(
        @NotBlank(message = "Room number is required")
        @Size(max = 10, message = "Room number must not exceed 10 characters")
        String roomNumber,

        @NotNull(message = "Room type is required")
        RoomType roomType,

        @NotNull(message = "Price is required")
        BigDecimal price
) {
}
