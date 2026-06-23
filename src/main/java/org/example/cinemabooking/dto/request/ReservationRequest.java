package org.example.cinemabooking.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReservationRequest {

    @NotNull(message = "Screening id is required")
    private Long screeningId;

    @NotNull(message = "Seat id is required")
    private Long seatId;
}
