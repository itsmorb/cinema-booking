package org.example.cinemabooking.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "screening_id", nullable = false)
    private Screening screening;

    @ManyToOne
    @JoinColumn(name = "seat_id", nullable = false)
    private Seat seat;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.PENDING;

    public enum Status {
        PENDING,
        RESERVED,
        CANCELLED
    }
}
