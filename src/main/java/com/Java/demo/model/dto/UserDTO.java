package com.Java.demo.model.dto;

import java.time.Instant;
import java.util.Date;

public class UserDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Date birthDate;

    // Optional fields depending on use case
    private Instant createdAt;
    private Instant updatedAt;

}
