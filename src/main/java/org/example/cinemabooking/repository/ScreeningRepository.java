package org.example.cinemabooking.repository;

import org.example.cinemabooking.entity.Screening;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ScreeningRepository extends JpaRepository<Screening, Long> {

    List<Screening> findByMovieId(Long movieId);

    List<Screening> findByStartTimeAfter(LocalDateTime startTime);

    List<Screening> findByMovieIdAndStartTimeAfter(Long movieId, LocalDateTime startTime);
}
