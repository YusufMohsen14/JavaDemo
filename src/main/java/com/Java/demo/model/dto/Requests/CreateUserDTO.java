package com.Java.demo.model.dto.Requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CreateUserDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private LocalDate birthDate;

}
