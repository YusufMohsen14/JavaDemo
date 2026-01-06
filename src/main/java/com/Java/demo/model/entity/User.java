package com.Java.demo.model.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Date;

@Entity
@Setter
@Getter
@Table(name = "user")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(nullable=false, unique=true, length=255)
    private String firstName;

    @Column(nullable=false, unique=true, length=255)
    private String lastName;

    @Column(nullable=false)
    private Date birthDate;

    @Column(nullable=false, length = 255)
    private String password;

    @Column(length = 255)
    private String refreshToken;

    @Column(nullable=false)
    private Instant createdAt;

    @Column(nullable=false)
    private Instant updatedAt;
}
