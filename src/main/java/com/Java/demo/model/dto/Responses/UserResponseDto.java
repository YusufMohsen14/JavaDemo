package com.Java.demo.model.dto.Responses;

import com.Java.demo.model.entity.UserContact;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Data
@Builder
@Setter
public class UserResponseDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private UserContact contact;
}
