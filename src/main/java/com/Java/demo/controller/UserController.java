package com.Java.demo.controller;

import com.Java.demo.model.dto.UserDTO;
import com.Java.demo.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping()
    public String addUser(@RequestBody UserDTO userDTO) {
        try {
            userService.createUser(userDTO);
        }catch (IllegalArgumentException e){
            return "Error: " + e.getMessage();
        }

        return "User created successfully";
    }




}
