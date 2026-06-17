package org.example.cinemabooking.entity;

import jakarta.persistence.*;
import lombok.Data;

import javax.management.relation.Role;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Role {
        USER, ADMIN
    }
}
