package com.example.hotelmanagementsystem.controller;

import com.example.hotelmanagementsystem.dto.request.RoomRequestDTO;
import com.example.hotelmanagementsystem.dto.response.RoomResponseDTO;
import com.example.hotelmanagementsystem.entity.RoomType;
import com.example.hotelmanagementsystem.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<RoomResponseDTO> createRoom(@RequestBody RoomRequestDTO requestDTO) {
        RoomResponseDTO responseDTO = roomService.saveRoom(requestDTO);
        return ResponseEntity. status(HttpStatus.CREATED).body(responseDTO);
    }
    @GetMapping("/{id}")
    public ResponseEntity<RoomResponseDTO> getRoomById(@PathVariable Long id){
        RoomResponseDTO responseDTO = roomService.findById(id);
        return ResponseEntity.ok(responseDTO);
    }
    @GetMapping
    public ResponseEntity<List<RoomResponseDTO>> getRooms(){
        return ResponseEntity.ok(roomService.findAll());
    }
    @GetMapping("/search")
    public ResponseEntity<RoomResponseDTO> searchRoom(
            @RequestParam String number,
            @RequestParam(required = false) RoomType type) {

        if (type != null) {
            RoomResponseDTO responseDTO = roomService.findRoomByRoomNumberAndRoomType(number, type);
            return ResponseEntity.ok(responseDTO);
        }
        RoomResponseDTO responseDTO = roomService.findRoomByRoomNumber(number);
        return ResponseEntity.ok(responseDTO);
    }
    @PatchMapping("/{id}/maintenance")
    public ResponseEntity<RoomResponseDTO> toggleRoomMaintenance(
            @PathVariable Long id,
            @RequestParam boolean maintenance) {

        roomService.toggleRoomMaintenance(id, maintenance);
        RoomResponseDTO responseDTO = roomService.findById(id);
        return ResponseEntity.ok(responseDTO);
    }
    @PutMapping("/{id}/details")
    public ResponseEntity<RoomResponseDTO> updateRoomDetails(@PathVariable Long id, @Valid @RequestBody RoomRequestDTO requestDTO) {
        RoomResponseDTO responseDTO = roomService.updateRoom(id, requestDTO);
        return ResponseEntity.ok(responseDTO);
    }
}
