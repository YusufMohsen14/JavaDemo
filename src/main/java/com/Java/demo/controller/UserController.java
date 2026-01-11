package com.Java.demo.controller;

import com.Java.demo.model.dto.Requests.CreateUserDTO;
import com.Java.demo.model.dto.Requests.UserLoginDTO;
import com.Java.demo.model.entity.User;
import com.Java.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping()
    public String addUser(@Valid @RequestBody CreateUserDTO createUserDTO) {
        userService.createUser(createUserDTO);
        return "User created successfully";
    }

    @PostMapping("/login")
    public String login(@Valid @RequestBody UserLoginDTO dto) {
        User user = userService.loginUser(dto);
        return "You logged in successfully!";
    }

}
