package com.example.hotelmanagementsystem.dto.request;

import com.example.hotelmanagementsystem.entity.RoomType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RoomRequestDTO(
        @NotBlank(message = "Room number is required")
        @Size(max = 10, message = "Room number must not exceed 10 characters")
        String roomNumber,

        @NotNull(message = "Room type is required")
        RoomType roomType,

        @NotNull(message = "Price is required")
        double price
) {
}
