package org.example.cinemabooking.controller;


import lombok.RequiredArgsConstructor;
import org.example.cinemabooking.dto.response.ScreeningResponse;
import org.example.cinemabooking.dto.response.SeatResponse;
import org.example.cinemabooking.service.ScreeningService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/screenings")
public class ScreeningController {

    private final ScreeningService screeningService;

    @GetMapping
    public ResponseEntity<List<ScreeningResponse>> getScreenings(
        @RequestParam(required = false) Long movieId) {
         if (movieId != null) {
             return ResponseEntity.ok(screeningService.getScreeningsByMovie(movieId));
         }
         return ResponseEntity.ok(screeningService.getAllScreenings());
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<ScreeningResponse>> getUpcomingScreenings(){
        return ResponseEntity.ok(screeningService.getUpcomingScreenings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScreeningResponse> getScreeningById(@PathVariable Long id){
        return ResponseEntity.ok(screeningService.getScreeningById(id));
    }

    @GetMapping("/{id}/seats")
    public ResponseEntity<List<SeatResponse>> getSeatsByScreeningId(@PathVariable Long id){
        return ResponseEntity.ok(screeningService.getSeatsByScreening(id));
    }
}
