package com.example.hotelmanagementsystem.util.mapper;

import com.example.hotelmanagementsystem.dto.request.ReservationRequestDTO;
import com.example.hotelmanagementsystem.dto.response.ReservationResponseDTO;
import com.example.hotelmanagementsystem.entity.Reservation;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {
    public ReservationResponseDTO toDTO(Reservation reservation) {
        return new ReservationResponseDTO(reservation.getReservationId(), reservation.getUser().getUserId(),
                reservation.getCheckInDate(), reservation.getCheckOutDate(), reservation.getRoomReservations(), reservation.getCreatedAt());
    }
}
