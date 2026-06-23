package org.example.cinemabooking.dto.response;

import lombok.Data;

@Data
public class SeatResponse {
    private Long id;
    private Integer rowNumber;
    private Integer seatNumber;
    private Boolean isReserved;
}
