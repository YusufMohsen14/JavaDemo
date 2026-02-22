package com.Java.demo.model.dto.Requests;

import com.Java.demo.enums.PetType;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class PetRequestDTO {
    @NotBlank
    @Size(min = 2, max = 50, message = "Pet name must be between 2 and 50 characters")
    @Pattern(regexp = "^[A-Za-z'-]+$", message = "Pet name must contain only letters, apostrophes, or hyphens")
    private  String name;

    @NotNull
    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;

    @NotNull
    private PetType type;
}
