package com.example.hotelmanagementsystem.service;

import com.example.hotelmanagementsystem.dto.request.ReservationRequestDTO;
import com.example.hotelmanagementsystem.dto.response.ReservationResponseDTO;
import com.example.hotelmanagementsystem.entity.Reservation;
import com.example.hotelmanagementsystem.entity.Room;
import com.example.hotelmanagementsystem.entity.User;
import com.example.hotelmanagementsystem.repository.ReservationRepository;
import com.example.hotelmanagementsystem.repository.RoomRepository;
import com.example.hotelmanagementsystem.repository.UserRepository;
import com.example.hotelmanagementsystem.util.mapper.ReservationMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;
    private final UserRepository userRepository; // lightweight access to the entity without any service overhead
    private final RoomRepository roomRepository; // lightweight access to the entity without any service overhead

   @Transactional(readOnly = true)
   public List<ReservationResponseDTO> findAll(){
       return reservationRepository.findAll()
               .stream()
               .map(reservationMapper::toDTO)
               .toList();
   }
    @Transactional
    public ReservationResponseDTO bookReservation(ReservationRequestDTO requestDTO) {
        validateDates(requestDTO.checkInDate(), requestDTO.checkOutDate());
        validateAvailability(requestDTO.roomIds(), requestDTO.checkInDate(), requestDTO.checkOutDate());

        User user = getUser(requestDTO.userId());
        List<Room> rooms = getRooms(requestDTO.roomIds());

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setCheckInDate(requestDTO.checkInDate());
        reservation.setCheckOutDate(requestDTO.checkOutDate());

        rooms.forEach(room -> reservation.addRoom(room));

        return reservationMapper.toDTO(reservationRepository.save(reservation));
    }
    private void validateDates(LocalDate checkInDate, LocalDate checkOutDate){
       if(!checkOutDate.isAfter(checkInDate)){
           throw new IllegalArgumentException("checkInDate is after checkOutDate");
       }
   }
   private void validateAvailability(List<Long> roomIds, LocalDate checkInDate, LocalDate checkOutDate){
       boolean isReserved = reservationRepository.existsOverlappingReservations(roomIds, checkInDate, checkOutDate);
       if(isReserved){
           throw new IllegalArgumentException("One or more rooms is already reserved");
       }
   }
    private User getUser(Long id){
       return userRepository.findById(id)
               .orElseThrow(() -> new EntityNotFoundException("User not found"));
   }
    private List<Room> getRooms(List<Long> roomIds){
       List<Room> rooms = roomRepository.findAllById(roomIds);
       if (rooms.size() != roomIds.size()) {
           throw new EntityNotFoundException("One or more room IDs are invalid");
       }
       return rooms;
   }
}
