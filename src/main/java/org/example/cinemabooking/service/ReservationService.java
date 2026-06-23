package org.example.cinemabooking.service;

import lombok.RequiredArgsConstructor;
import org.example.cinemabooking.dto.request.ReservationRequest;
import org.example.cinemabooking.dto.response.ReservationResponse;
import org.example.cinemabooking.entity.Reservation;
import org.example.cinemabooking.entity.Screening;
import org.example.cinemabooking.entity.Seat;
import org.example.cinemabooking.entity.User;
import org.example.cinemabooking.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ScreeningRepository screeningRepository;
    private final UserRepository userRepository;
    private final SeatRepository seatRepository;

    public ReservationResponse createReservation(ReservationRequest reservationRequest, String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Screening screening = screeningRepository.findById(reservationRequest.getScreeningId())
                .orElseThrow(() -> new RuntimeException("Screening not found"));
        Seat seat = seatRepository.findById(reservationRequest.getSeatId())
                .orElseThrow(() -> new RuntimeException("Seat not found"));

        if ( !seat.getScreening().getId().equals(screening.getId()) ) {
            throw new RuntimeException("Screening id mismatch, seat does not belong to this screening");
        }

        if (reservationRepository.existsBySeatIdAndStatusNot(seat.getId(), Reservation.Status.CANCELLED)) {
            throw new RuntimeException("Reservation already cancelled");
        }

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setScreening(screening);
        reservation.setSeat(seat);
        reservation.setStatus(Reservation.Status.PENDING);

        seat.setIsReserved(true);
        seatRepository.save(seat);

        Reservation savedReservation = reservationRepository.save(reservation);
        return mapToResponse(savedReservation);
    }

    public ReservationResponse confirmReservation(Long reservationId, String username) {

        Reservation reservation = getReservationAndValidateOwner(reservationId, username);

        if (reservation.getStatus() != Reservation.Status.PENDING) {
            throw new RuntimeException("Only PENDING reservations may be confirmed");
        }

        reservation.setStatus(Reservation.Status.RESERVED);
        return mapToResponse(reservationRepository.save(reservation));
    }

    public ReservationResponse cancelReservation(Long reservationId, String username) {
        Reservation reservation = getReservationAndValidateOwner(reservationId, username);

        if (reservation.getStatus() == Reservation.Status.CANCELLED) {
            throw new RuntimeException("Reservation already cancelled");
        }

        reservation.setStatus(Reservation.Status.CANCELLED);
        Seat seat = reservation.getSeat();
        seat.setIsReserved(false);
        seatRepository.save(seat);

        return mapToResponse(reservationRepository.save(reservation));
    }

    public List<ReservationResponse> getUserReservations(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return reservationRepository.findByUserId(user.getId())
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private Reservation getReservationAndValidateOwner(Long reservationId, String username) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        if (!reservation.getUser().getUsername().equals(username)) {
            throw new RuntimeException("You don't have permission to modify this reservation");
            }

        return reservation;
    }


    private ReservationResponse mapToResponse(Reservation reservation) {

        ReservationResponse response = new ReservationResponse();
        response.setId(reservation.getId());
        response.setScreeningId(reservation.getScreening().getId());
        response.setMovieTitle(reservation.getScreening().getMovie().getTitle());
        response.setScreeningStartTime(reservation.getScreening().getStartTime());
        response.setHallName(reservation.getScreening().getHallName());
        response.setRowNumber(reservation.getSeat().getRowNumber());
        response.setSeatNumber(reservation.getSeat().getSeatNumber());
        response.setStatus(reservation.getStatus().name());
        response.setCreatedAt(reservation.getCreatedAt());
        return response;
    }
}
