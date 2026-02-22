package com.Java.demo.model.dto.Responses;

import com.Java.demo.enums.PetType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class PetResponseDTO {
    private Long id;
    private String name;
    private LocalDate birthDate;
    private PetType type;
    private Instant createdAt;
}

