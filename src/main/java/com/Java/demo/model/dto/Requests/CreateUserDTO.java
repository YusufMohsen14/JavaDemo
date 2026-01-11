package com.Java.demo.model.dto.Requests;

import jakarta.persistence.Column;
import lombok.Getter;

import java.time.Instant;
import java.time.LocalDate;

@Getter
public class CreateUserDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private LocalDate birthDate;
    private String country;
    private String city;
    private String phoneNumber;

}
