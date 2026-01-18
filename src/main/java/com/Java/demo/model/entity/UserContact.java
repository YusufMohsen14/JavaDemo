package com.Java.demo.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name = "user_contact")
@Setter
@Getter
public class UserContact {

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String country;

    @Column(nullable = false, length = 255)
    private String city;

    @Column(nullable = false, unique = true, length = 255)
    private String phoneNumber;

    @CreationTimestamp
    @Column(nullable=false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(nullable=false)
    private Instant updatedAt;

}
