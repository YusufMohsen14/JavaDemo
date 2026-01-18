package com.Java.demo.controller;

import com.Java.demo.model.dto.Requests.CreateUserDTO;
import com.Java.demo.model.dto.Requests.UserLoginDTO;
import com.Java.demo.model.entity.User;
import com.Java.demo.service.JWTService;
import com.Java.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final JWTService.JwtService jwtService;

    @PostMapping("/register")
    public String addUser(@Valid @RequestBody CreateUserDTO createUserDTO) {
        userService.createUser(createUserDTO);
        return "User created successfully";
    }

    @PostMapping("/login")
    public String login(@Valid @RequestBody UserLoginDTO dto) {
        User user = userService.loginUser(dto);
        String token = jwtService.generateToken(user);
        return "You logged in successfully!";
    }

}
