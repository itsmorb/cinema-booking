package org.example.cinemabooking.controller;

import lombok.RequiredArgsConstructor;
import org.example.cinemabooking.dto.response.MovieResponse;
import org.example.cinemabooking.entity.Movie;
import org.example.cinemabooking.service.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping
    public ResponseEntity<List<MovieResponse>> getMovies(
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String title) {

        if (genre != null) {
            return ResponseEntity.ok(movieService.getMoviesByGenre(genre));
        }
        if (title != null) {
            return ResponseEntity.ok(movieService.getMoviesByTitle(title));
        }
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @GetMapping ("/{id}")
    public ResponseEntity<MovieResponse> getMovieById(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.getMovieById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MovieResponse> addMovie(@RequestBody Movie movie) {
        return ResponseEntity.ok(movieService.addMovie(movie));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MovieResponse> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.ok(movieService.getMovieById(id));
    }
}
