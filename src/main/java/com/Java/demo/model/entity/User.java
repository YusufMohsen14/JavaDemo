package com.Java.demo.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Setter
@Getter
@Table(name = "users")

public class User {

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserContact contact;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(nullable=false, length=255)
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    @Pattern(regexp = "^[A-Za-z'-]+$", message = "First name must contain only letters, apostrophes, or hyphens")
    private String firstName;

    @Column(nullable=false, length=255)
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    @Pattern(regexp = "^[A-Za-z'-]+$", message = "First name must contain only letters, apostrophes, or hyphens")
    private String lastName;

    @Column(nullable=false)
    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;

    @Transient
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, one number, and one special character."
    )
    private String rawPassword;

    @Column(nullable=false, length = 255)
    private String password;

    @Column(length = 255)
    private String refreshToken;

    @Column(nullable=false)
    private Instant createdAt;

    @Column(nullable=false)
    private Instant updatedAt;
}
