package org.example.cinemabooking.service;

import lombok.RequiredArgsConstructor;
import org.example.cinemabooking.dto.response.ScreeningResponse;
import org.example.cinemabooking.dto.response.SeatResponse;
import org.example.cinemabooking.entity.Movie;
import org.example.cinemabooking.entity.Screening;
import org.example.cinemabooking.entity.Seat;
import org.example.cinemabooking.repository.MovieRepository;
import org.example.cinemabooking.repository.ScreeningRepository;
import org.example.cinemabooking.repository.SeatRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScreeningService {

    private final ScreeningRepository screeningRepository;
    private final SeatRepository seatRepository;
    private final MovieRepository movieRepository;

    public ScreeningResponse addScreening(Screening screening) {
        Movie movie = movieRepository.findById(screening.getMovie().getId())
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        screening.setMovie(movie);
        Screening saved = screeningRepository.save(screening);

        for (int row = 1; row <= 5; row++) {
            for (int seat = 1; seat <= 10; seat++) {
                Seat newSeat = new Seat();
                newSeat.setScreening(saved);
                newSeat.setRowNumber(row);
                newSeat.setSeatNumber(seat);
                newSeat.setIsReserved(false);
                seatRepository.save(newSeat);
            }
        }

        return mapToResponse(saved);
    }

    public void deleteScreening(Long id) {
        if (!screeningRepository.existsById(id)) {
            throw new RuntimeException("Screening not found with id: " + id);
        }
        screeningRepository.deleteById(id);
    }

    public List<ScreeningResponse> getAllScreenings() {
        return screeningRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<ScreeningResponse> getScreeningsByMovie(Long movieId) {
        return screeningRepository.findByMovieId(movieId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<ScreeningResponse> getUpcomingScreenings() {
        return screeningRepository.findByStartTimeAfter(LocalDateTime.now())
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ScreeningResponse getScreeningById(Long id) {
        Screening screening = screeningRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Screening not found with id: " + id));
        return mapToResponse(screening);
    }

    public List<SeatResponse> getSeatsByScreening(Long screeningId) {
        return seatRepository.findByScreeningId(screeningId)
                .stream()
                .map(this::mapSeatToResponse)
                .collect(Collectors.toList());
    }

    private ScreeningResponse mapToResponse(Screening screening) {
        ScreeningResponse response = new ScreeningResponse();
        response.setId(screening.getId());
        response.setMovieId(screening.getMovie().getId());
        response.setMovieTitle(screening.getMovie().getTitle());
        response.setStartTime(screening.getStartTime());
        response.setEndTime(screening.getEndTime());
        response.setHallName(screening.getHallName());
        response.setAvailableSeats(
                seatRepository.findByScreeningIdAndIsReserved(screening.getId(), false).size()
        );
        return response;
    }

    private SeatResponse mapSeatToResponse(Seat seat) {
        SeatResponse response = new SeatResponse();
        response.setId(seat.getId());
        response.setRowNumber(seat.getRowNumber());
        response.setSeatNumber(seat.getSeatNumber());
        response.setIsReserved(seat.getIsReserved());
        return response;
    }
}
