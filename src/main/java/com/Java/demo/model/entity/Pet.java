package com.Java.demo.model.entity;

import com.Java.demo.enums.PetType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.Instant;
import java.time.LocalDate;


@Data
@Entity
@Table(name = "pets")
public class Pet {

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private  String name;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PetType type;

    @CreationTimestamp
    @Column(nullable=false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(nullable=false)
    private Instant updatedAt;
}
