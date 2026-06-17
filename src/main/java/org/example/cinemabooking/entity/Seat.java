package org.example.cinemabooking.entity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "seats")
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "screening_id", nullable = false)
    private Screening screening;

    @Column(nullable = false)
    private Integer rowNumber;

    @Column(nullable = false)
    private Integer seatNumber;

    @Column(nullable = false)
    private Boolean isReserved = false;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User reservedBy;
}
