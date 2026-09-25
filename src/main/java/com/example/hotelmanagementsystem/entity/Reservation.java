package com.example.hotelmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long reservationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<RoomReservation> roomReservations = new ArrayList<>();

    @Column(name = "check_in_date", nullable = false)
    private LocalDate checkInDate;

    @Column(name = "check_out_date", nullable = false)
    private LocalDate checkOutDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private ReservationStatus status;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "price_per_night" , nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;

    public void addRoomReservation(RoomReservation roomReservation) {
        roomReservations.add(roomReservation);
        roomReservation.setReservation(this);
    }
    public void addRoom(Room room) {
        RoomReservation roomReservation = new RoomReservation();
        roomReservation.setRoom(room);
        roomReservation.setTotalPrice(room.getPrice());
        addRoomReservation(roomReservation);
    }
    public void removeRoomReservation(RoomReservation roomReservation) {
        roomReservations.remove(roomReservation);
        roomReservation.setReservation(null);
    }
}
