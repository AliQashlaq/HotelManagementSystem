package com.example.hotelmanagementsystem.service;

import com.example.hotelmanagementsystem.dto.request.ReservationRequestDTO;
import com.example.hotelmanagementsystem.dto.response.ReservationResponseDTO;
import com.example.hotelmanagementsystem.entity.Reservation;
import com.example.hotelmanagementsystem.entity.ReservationStatus;
import com.example.hotelmanagementsystem.entity.Room;
import com.example.hotelmanagementsystem.entity.User;
import com.example.hotelmanagementsystem.repository.ReservationRepository;
import com.example.hotelmanagementsystem.util.mapper.ReservationMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;
    private final UserService userService;
    private final RoomService roomService;

    @Transactional(readOnly = true)
    public ReservationResponseDTO findById(Long id) {
        return reservationMapper.toDTO(getReservationOrThrow(id));
    }

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
        reservation.setStatus(ReservationStatus.CONFIRMED);

        rooms.forEach(room -> reservation.addRoom(room));

        return reservationMapper.toDTO(reservationRepository.save(reservation));
    }
    @Transactional
    public void cancelReservation(Long id) {
        Reservation reservation = getReservationOrThrow(id);

        if (reservation.getStatus() == ReservationStatus.CANCELLED) {
            throw new IllegalStateException("Reservation is already cancelled.");
        }
        if (reservation.getCheckInDate().isBefore(LocalDate.now())) {
            throw new IllegalStateException("Cannot cancel a reservation that has already started or passed.");
        }

        reservation.setStatus(ReservationStatus.CANCELLED);
    }
    private void validateDates(LocalDate checkInDate, LocalDate checkOutDate){
       if(!checkOutDate.isAfter(checkInDate)){
           throw new IllegalArgumentException("checkInDate is after checkOutDate");
       }
   }
   private void validateAvailability(Set<Long> roomIds, LocalDate checkInDate, LocalDate checkOutDate){
       boolean isReserved = reservationRepository.existsOverlappingReservations(roomIds, checkInDate, checkOutDate);
       if(isReserved){
           throw new IllegalArgumentException("One or more rooms is already reserved");
       }
   }
    private User getUser(Long id){
       return userService.findEntityById(id);
   }
    private List<Room> getRooms(Set<Long> roomIds){
       return roomService.findEntitiesByIds(roomIds);
   }
    private Reservation getReservationOrThrow(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found with ID: " + id));
    }
}
