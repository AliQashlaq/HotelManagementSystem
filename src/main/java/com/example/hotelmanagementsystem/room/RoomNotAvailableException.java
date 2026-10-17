package com.example.hotelmanagementsystem.room;

public class RoomNotAvailableException extends RuntimeException{
    public RoomNotAvailableException(String message){
        super(message);
    }
}
