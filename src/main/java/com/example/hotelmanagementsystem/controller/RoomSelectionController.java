package com.example.hotelmanagementsystem.controller;

import com.example.hotelmanagementsystem.entity.Room;
import com.example.hotelmanagementsystem.entity.RoomType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/rooms")
@SessionAttributes("reservation")
public class RoomSelectionController {

    @ModelAttribute
    public void addRoom(Model model){
        List<Room> rooms = Arrays.asList(
                createRoom(101L, RoomType.SINGLE),
                createRoom(102L, RoomType.SINGLE),
                createRoom(201L, RoomType.DOUBLE),
                createRoom(202L, RoomType.DOUBLE),
                createRoom(301L, RoomType.SUITE),
                createRoom(302L, RoomType.SUITE)
        );
        RoomType[] types = RoomType.values();
        for (RoomType type : types) {
            model.addAttribute(type.name().toLowerCase(), filterByType(rooms, type));
        }
    }
    private List<Room> filterByType(List<Room> rooms, RoomType type) {
        return rooms.stream()
                .filter(r -> r.getRoomType().equals(type))
                .collect(Collectors.toList());
    }
    private Room createRoom(long id, RoomType type) {
        Room room = new Room();
        room.setId(id);
        room.setRoomType(type);
        room.setOccupied(false);
        return room;
    }
}
