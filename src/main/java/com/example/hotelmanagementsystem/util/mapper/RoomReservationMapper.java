package com.example.hotelmanagementsystem.util.mapper;

import com.example.hotelmanagementsystem.dto.response.RoomReservationResponseDTO;
import com.example.hotelmanagementsystem.entity.RoomReservation;
import org.springframework.stereotype.Component;

@Component
public class RoomReservationMapper {
    public RoomReservationResponseDTO toDTO(RoomReservation roomReservation) {
        return new RoomReservationResponseDTO(roomReservation.getId(), roomReservation.getRoom().getRoomId(),
                roomReservation.getRoom().getRoomNumber(), roomReservation.getPrice());
    }
}
