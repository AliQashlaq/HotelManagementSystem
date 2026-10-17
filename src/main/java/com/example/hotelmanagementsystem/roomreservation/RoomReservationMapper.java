package com.example.hotelmanagementsystem.roomreservation;

import org.springframework.stereotype.Component;

@Component
public class RoomReservationMapper {
    public RoomReservationResponseDTO toDTO(RoomReservation roomReservation) {
        return new RoomReservationResponseDTO(roomReservation.getId(), roomReservation.getRoom().getRoomId(),
                roomReservation.getRoom().getRoomNumber(), roomReservation.getTotalPrice());
    }
}
