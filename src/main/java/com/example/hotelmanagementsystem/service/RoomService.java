package com.example.hotelmanagementsystem.service;

import com.example.hotelmanagementsystem.dto.request.RoomRequestDTO;
import com.example.hotelmanagementsystem.dto.response.RoomResponseDTO;
import com.example.hotelmanagementsystem.entity.Room;
import com.example.hotelmanagementsystem.entity.RoomType;
import com.example.hotelmanagementsystem.exception.DuplicateResourceException;
import com.example.hotelmanagementsystem.repository.RoomRepository;
import com.example.hotelmanagementsystem.util.mapper.RoomMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    @Transactional(readOnly = true)
    public RoomResponseDTO findById(Long id){
        return roomMapper.toDTO(getRoomOrThrow(id));
    }
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
    @Transactional(readOnly = true)
    public List<RoomResponseDTO> findAllById(Set<Long> ids) {
        return roomRepository.findAllById(ids)
                .stream()
                .map(roomMapper::toDTO)
                .toList();
    }
    @Transactional(readOnly = true)
    public List<Room> findEntitiesByIds(Set<Long> ids) {
        List<Room> rooms = roomRepository.findAllById(ids);
        if (rooms.size() != ids.size()) {
            throw new EntityNotFoundException("One or more room IDs are invalid");
        }
        return rooms;
    }
    @Transactional
    public RoomResponseDTO saveRoom(RoomRequestDTO requestDTO) {
        if(roomRepository.existsByRoomNumber(requestDTO.roomNumber())){
            throw new DuplicateResourceException("Room with room number already exists");
        }
        Room room = new  Room();

        room.setRoomNumber(requestDTO.roomNumber());
        room.setRoomType(requestDTO.roomType());
        room.setPrice(requestDTO.price());

        return roomMapper.toDTO(roomRepository.save(room));
    }
    @Transactional
    public RoomResponseDTO updateRoom(Long id, RoomRequestDTO requestDTO) {
        Room room = getRoomOrThrow(id);

        if (!room.getRoomNumber().equalsIgnoreCase(requestDTO.roomNumber())
                && roomRepository.existsByRoomNumber(requestDTO.roomNumber())) {
            throw new DuplicateResourceException("Room number is already in use: " + requestDTO.roomNumber());
        }

        room.setRoomNumber(requestDTO.roomNumber());
        room.setRoomType(requestDTO.roomType());
        room.setPrice(requestDTO.price());

        return roomMapper.toDTO(room);
    }
    @Transactional
    public void toggleRoomMaintenance(Long id, boolean maintenance) {
        Room room = getRoomOrThrow(id);
        room.setOccupied(maintenance);
    }
    private Room getRoomOrThrow(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Room not found with ID: " + id));
    }
}
