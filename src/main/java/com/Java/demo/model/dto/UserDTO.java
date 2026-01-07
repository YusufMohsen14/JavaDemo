package com.Java.demo.model.dto;

import lombok.Getter;

import java.time.Instant;
import java.time.LocalDate;

@Getter
public class UserDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private LocalDate birthDate;

}
