package com.example.hotelmanagementsystem.dto.response;

import com.example.hotelmanagementsystem.entity.RoomType;

public record RoomResponseDTO(
        Long roomId,
        String roomNumber,
        RoomType roomType,
        String displayName,
        double basePrice
) {
}
