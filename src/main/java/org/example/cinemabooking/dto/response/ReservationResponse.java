package org.example.cinemabooking.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationResponse {

    private Long id;
    private Long screeningId;
    private String movieTitle;
    private LocalDateTime screeningStartTime;
    private String hallName;
    private Integer rowNumber;
    private Integer seatNumber;
    private String status;

    private LocalDateTime createdAt;
}
