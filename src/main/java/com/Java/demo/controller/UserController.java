package com.Java.demo.controller;

import com.Java.demo.exception.customException.ResourceExistException;
import com.Java.demo.model.dto.Requests.CreateUserDTO;
import com.Java.demo.model.dto.Responses.UserLoginDTO;
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
    public String addUser(@RequestBody CreateUserDTO createUserDTO) {
        userService.createUser(createUserDTO);
        return "User created successfully";
    }





}
