package org.example.cinemabooking.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.cinemabooking.dto.request.ReservationRequest;
import org.example.cinemabooking.dto.response.ReservationResponse;
import org.example.cinemabooking.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(
            @Valid @RequestBody ReservationRequest reservationRequest,
            @AuthenticationPrincipal UserDetails userDetails) {

        return ResponseEntity.ok(
                reservationService.createReservation(reservationRequest, userDetails.getUsername())
        );
    }

    @GetMapping("/my")
    public ResponseEntity<List<ReservationResponse>> getMyReservations(
            @AuthenticationPrincipal UserDetails userDetails) {

        return ResponseEntity.ok(reservationService.getUserReservations(userDetails.getUsername()));
    }

    @PutMapping("/{id}/confirm")
    public ResponseEntity<ReservationResponse> confirmReservation(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {

        return ResponseEntity.ok(reservationService.confirmReservation(id, userDetails.getUsername()));
    }

    @DeleteMapping("/{id}/confirm")
    public ResponseEntity<ReservationResponse> cancelReservation(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {

        return ResponseEntity.ok(reservationService.cancelReservation(id, userDetails.getUsername()));
    }
}
