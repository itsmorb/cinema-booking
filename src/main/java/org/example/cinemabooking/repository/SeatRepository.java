package org.example.cinemabooking.repository;

import org.example.cinemabooking.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {

    List<Seat> findByScreeningId(Long screeningId);

    List<Seat> findByScreeningIdAndIsReserved(Long screeningId, Boolean isReserved);

    Boolean existsByScreeningIdAndRowNumberAndSeatNumber(Long screeningId, Integer rowNumber, Integer seatNumber);
}
