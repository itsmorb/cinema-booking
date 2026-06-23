package org.example.cinemabooking.dto.response;

import lombok.Data;

@Data
public class MovieResponse {

    private Long id;
    private String title;
    private String description;
    private String genre;
}
