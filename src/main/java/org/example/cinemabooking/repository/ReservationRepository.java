package org.example.cinemabooking.repository;

import org.example.cinemabooking.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByScreeningId(Long screeningId);

    List<Reservation> findByUserId(Long userId);

    Boolean existsBySeatIdAndStatusNot(Long seatId, Reservation.Status status);
}
