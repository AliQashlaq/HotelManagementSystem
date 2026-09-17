package com.example.hotelmanagementsystem.util.mapper;

import com.example.hotelmanagementsystem.dto.response.RoomResponseDTO;
import com.example.hotelmanagementsystem.entity.Room;
import org.springframework.stereotype.Component;

@Component
public class RoomMapper {
    public RoomResponseDTO toDTO(Room room) {
        return new RoomResponseDTO(room.getRoomId(), room.getRoomNumber(), room.getRoomType(), room.getRoomType().getDisplayName(), room.getRoomType().getBasePrice());
    }
}
