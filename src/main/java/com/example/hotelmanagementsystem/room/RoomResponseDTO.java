package com.example.hotelmanagementsystem.room;

public record RoomResponseDTO(
        Long roomId,
        String roomNumber,
        RoomType roomType,
        String displayName,
        double basePrice
) {
}
