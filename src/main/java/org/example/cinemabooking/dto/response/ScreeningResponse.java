package org.example.cinemabooking.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ScreeningResponse {

    private Long id;
    private Long movieId;
    private String movieTitle;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String hallName;
    private Integer availableSeats;
}
