package com.Java.demo.model.dto.Responses;

import com.Java.demo.model.entity.UserContact;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Data
@Builder
@Setter
public class UserDto {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private UserContact contact;
}
