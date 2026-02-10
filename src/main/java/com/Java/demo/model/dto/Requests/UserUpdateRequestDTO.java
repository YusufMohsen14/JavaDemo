package com.Java.demo.model.dto.Requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class UserUpdateRequestDTO {
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    @Pattern(regexp = "^[A-Za-z'-]+$", message = "First name must contain only letters, apostrophes, or hyphens")
    private String firstName;

    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    @Pattern(regexp = "^[A-Za-z'-]+$", message = "First name must contain only letters, apostrophes, or hyphens")
    private String lastName;

    @Email
    private String email;

    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;

    private UserContactDTO contact;

}
