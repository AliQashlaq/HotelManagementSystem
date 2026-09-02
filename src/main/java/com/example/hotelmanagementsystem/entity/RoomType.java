package com.example.hotelmanagementsystem.entity;

import lombok.Getter;

@Getter
public enum RoomType {
    SINGLE("Single Room", 100.0),
    DOUBLE("Double Room", 150.0),
    SUITE("Executive Suite", 300.0);

    private final String displayName;
    private final double basePrice;

    RoomType(String displayName, double basePrice) {
        this.displayName = displayName;
        this.basePrice = basePrice;
    }

}
