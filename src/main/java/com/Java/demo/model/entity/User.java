package com.Java.demo.model.entity;

import com.Java.demo.enums.Role;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDate;

@Data
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User{

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private UserContact contact;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(nullable=false, length=255)
    private String firstName;

    @Column(nullable=false, length=255)
    private String lastName;

    @Column(nullable=false)
    private LocalDate birthDate;

    @Transient
    private String rawPassword;

    @Column(nullable=false, length = 255)
    private String password;

    @Column(length = 255, unique = true)
    private String refreshToken;

    @CreationTimestamp
    @Column(nullable=false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(nullable=false)
    private Instant updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
}
