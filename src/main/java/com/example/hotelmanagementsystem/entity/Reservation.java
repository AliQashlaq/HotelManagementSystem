package com.example.hotelmanagementsystem.entity;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class Reservation {
    private final Long id;
    private final User user = new User();
    private final List<Room> rooms = new ArrayList<>();
    private final LocalDate checkInDate;
    private final LocalDate checkOutDate;

    public void addRoom(Room room) {
        rooms.add(room);
    }
}
