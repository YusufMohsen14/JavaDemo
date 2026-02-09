package com.Java.demo.model.dto.Requests;
import lombok.Getter;

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
