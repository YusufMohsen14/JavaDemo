package com.Java.demo.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "user_contact")
@Setter
@Getter
public class UserContact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false, length = 255)
    private String country;

    @Column(nullable = false, length = 255)
    private String city;

    @Column(nullable = false, unique = true, length = 255)
    private String phoneNumber;

    @Column(nullable=false)
    private Instant createdAt;

    @Column(nullable=false)
    private Instant updatedAt;

}
