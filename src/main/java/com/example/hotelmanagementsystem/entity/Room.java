package com.example.hotelmanagementsystem.entity;

import lombok.Data;

@Data
public class Room {
    private Long id;
    private RoomType roomType;
    private boolean occupied;
}
