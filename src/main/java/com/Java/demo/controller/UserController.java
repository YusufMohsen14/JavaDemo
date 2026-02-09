package com.Java.demo.controller;

import com.Java.demo.model.dto.Requests.CreateUserDTO;
import com.Java.demo.model.dto.Requests.RefreshTokenRequestDTO;
import com.Java.demo.model.dto.Requests.UserLoginDTO;
import com.Java.demo.model.dto.Requests.UserRequestDTO;
import com.Java.demo.model.dto.Response.LoginResponseDTO;
import com.Java.demo.model.dto.Response.RefreshTokenResponseDTO;
import com.Java.demo.model.dto.Responses.UserDto;
import com.Java.demo.model.entity.User;
import com.Java.demo.security.JWTUtil;
import com.Java.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final JWTUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@Valid @RequestBody CreateUserDTO createUserDTO) {
        userService.createUser(createUserDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", "User created successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody UserLoginDTO dto) {
        LoginResponseDTO response = userService.loginUser(dto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponseDTO> refreshToken(
            @Valid @RequestBody RefreshTokenRequestDTO request) {
        RefreshTokenResponseDTO response = userService.refreshAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<UserDto> users = userService.getAllUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@RequestParam long id) {
        try {
            userService.deleteById(id);
            return ResponseEntity.ok(Map.of("message", "User deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody UserRequestDTO userRequestDTO) {
        try {
            UserDto userDto = userService.updateUser(userRequestDTO);
            return ResponseEntity.ok(userDto);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

}
