package com.example.hotelmanagementsystem.util.mapper;

import com.example.hotelmanagementsystem.dto.response.ReservationResponseDTO;
import com.example.hotelmanagementsystem.entity.Reservation;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ReservationMapper {
    public ReservationResponseDTO toDTO(Reservation reservation) {
        Set<Long> roomIds = reservation.getRoomReservations() == null
                ? Collections.emptySet()
                : reservation.getRoomReservations().stream()
                  .map(rr -> rr.getRoom().getRoomId())
                  .collect(Collectors.toSet());


        return new ReservationResponseDTO(
                reservation.getReservationId(),
                reservation.getUser().getUserId(),
                roomIds,
                reservation.getCheckInDate(),
                reservation.getCheckOutDate(),
                reservation.getStatus(),
                reservation.getTotalPrice());
    }
}
