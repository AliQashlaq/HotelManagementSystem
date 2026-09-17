package com.example.hotelmanagementsystem.service;

import com.example.hotelmanagementsystem.dto.request.RoomRequestDTO;
import com.example.hotelmanagementsystem.dto.response.RoomResponseDTO;
import com.example.hotelmanagementsystem.entity.Room;
import com.example.hotelmanagementsystem.entity.RoomType;
import com.example.hotelmanagementsystem.repository.RoomRepository;
import com.example.hotelmanagementsystem.util.mapper.RoomMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    @Transactional(readOnly = true)
    public RoomResponseDTO findRoomByRoomNumber(String roomNumber) {
        return roomRepository.findByRoomNumber(roomNumber)
                .map(roomMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Room " + roomNumber + " not found"));
    }
    @Transactional(readOnly = true)
    public RoomResponseDTO findRoomByRoomNumberAndRoomType(String roomNumber, RoomType roomType) {
        return roomRepository.findByRoomNumberAndRoomType(roomNumber, roomType)
                .map(roomMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Room " + roomNumber + " " + roomType + " not found"));
    }

    @Transactional(readOnly = true)
    public List<RoomResponseDTO> findAll() {
        return roomRepository.findAll()
                .stream()
                .map(roomMapper::toDTO)
                .toList();
    }
    @Transactional
    public RoomResponseDTO saveRoom(RoomRequestDTO requestDTO) {
        if(roomRepository.existsByRoomNumber(requestDTO.roomNumber())){
            throw new IllegalArgumentException("Room with room number already exists");
        }
        Room room = new  Room();

        room.setRoomNumber(requestDTO.roomNumber());
        room.setRoomType(requestDTO.roomType());

        return roomMapper.toDTO(roomRepository.save(room));
    }
}
