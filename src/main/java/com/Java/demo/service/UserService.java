package com.Java.demo.service;

import com.Java.demo.enums.Role;
import com.Java.demo.exception.customException.InvalidTokenException;
import com.Java.demo.exception.customException.LoginAuthenticationException;
import com.Java.demo.exception.customException.ResourceExistException;
import com.Java.demo.exception.customException.ResourceNotFoundException;
import com.Java.demo.mapper.UserContactMapper;
import com.Java.demo.mapper.UserCreateMapper;
import com.Java.demo.mapper.UserUpdateMapper;
import com.Java.demo.model.dto.Requests.UserLoginDTO;
import com.Java.demo.model.dto.Requests.UserRequestDTO;
import com.Java.demo.model.dto.Requests.UserUpdateRequestDTO;
import com.Java.demo.model.dto.Responses.LoginResponseDTO;
import com.Java.demo.model.dto.Responses.RefreshTokenResponseDTO;
import com.Java.demo.model.dto.Responses.UserResponseDto;
import com.Java.demo.model.entity.User;
import com.Java.demo.model.entity.UserContact;
import com.Java.demo.repository.UserContactRepository;
import com.Java.demo.repository.UserRepository;
import com.Java.demo.security.JWTUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserContactRepository userContactRepository;
    private final JWTUtil jwtUtil;
    private final UserCreateMapper userCreateMapper;
    private final UserUpdateMapper userUpdateMapper;
    private final UserContactMapper userContactMapper;

    public void createUser(@Valid UserRequestDTO userRequestDTO) {
        Optional<User> existUsers = userRepository.findByEmail(userRequestDTO.getEmail());
        if (existUsers.isPresent()) {
            throw new ResourceExistException("User with email " + userRequestDTO.getEmail() + " already exists.");
        }
        User newUser = userCreateMapper.toUser(userRequestDTO);

        newUser.setRawPassword(userRequestDTO.getPassword());
        newUser.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        newUser.setRefreshToken(jwtUtil.generateRefreshToken(newUser));
        newUser.setRole(Role.valueOf("USER"));

        if (userRequestDTO.getContact() != null) {
            UserContact userContact = userContactMapper.toUser(userRequestDTO.getContact());
            userContact.setUser(newUser);
            newUser.setContact(userContact);
        }

        userRepository.save(newUser);
    }

    public LoginResponseDTO loginUser(UserLoginDTO dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new LoginAuthenticationException("Invalid email or password"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new LoginAuthenticationException("Invalid email or password");
        }

        String accessToken = jwtUtil.generateAccessToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);

        user.setRefreshToken(refreshToken);
        user.setUpdatedAt(Instant.now());
        userRepository.save(user);

        return LoginResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(user.getId())
                .email(user.getEmail())
                .build();
    }

    public RefreshTokenResponseDTO refreshAccessToken(String refreshToken) {
        if (!jwtUtil.validateToken(refreshToken)) {
            throw new InvalidTokenException("Invalid or expired refresh token");
        }

        String email = jwtUtil.getEmailFromToken(refreshToken);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidTokenException("Invalid refresh token"));

        if (!refreshToken.equals(user.getRefreshToken())) {
            throw new InvalidTokenException("Invalid refresh token");
        }

        String newAccessToken = jwtUtil.generateAccessToken(user);
        String newRefreshToken = jwtUtil.generateRefreshToken(user);

        user.setRefreshToken(newRefreshToken);
        user.setUpdatedAt(Instant.now());
        userRepository.save(user);

        return RefreshTokenResponseDTO.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }

    public void logout(String refreshToken) {
        if (refreshToken == null || refreshToken.isEmpty()) {
            return;
        }

        try {
            String email = jwtUtil.getEmailFromToken(refreshToken);
            Optional<User> userOpt = userRepository.findByEmail(email);

            if (userOpt.isPresent()) {
                User user = userOpt.get();
                if (refreshToken.equals(user.getRefreshToken())) {
                    user.setRefreshToken(null);
                    user.setUpdatedAt(Instant.now());
                    userRepository.save(user);
                }
            }
        } catch (Exception e) {
            // Silently fail for logout
        }
    }

    public void logoutAll(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setRefreshToken(null);
        user.setUpdatedAt(Instant.now());
        userRepository.save(user);
    }

    public List<UserResponseDto> getAllUsers() {
        List<User> allUsers =  userRepository.findAll();
        return allUsers.stream().map(user -> {
            UserContact userContact = userContactRepository.findByUserId(user.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("User contact not found for user id: " + user.getId()));

            return UserResponseDto.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .contact(userContact)
                    .build();
        }).toList();
    }

    public void deleteById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id " + id);
        }
        userRepository.deleteById(id);
    }

    @Transactional
    public UserResponseDto updateUser(long id, @Valid UserUpdateRequestDTO userUpdateRequestDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User with id " + id + " not found."));

        userUpdateMapper.updateUser(userUpdateRequestDTO, user);
        if (userUpdateRequestDTO.getContact() != null) {
            UserContact contact = user.getContact();
            if (contact == null) {
                throw new ResourceNotFoundException(
                        "User contact not found for user id " + id);
            }
            userUpdateMapper.updateUserContact(userUpdateRequestDTO.getContact(), contact);
        }
        User updatedUser = userRepository.save(user);
        return UserResponseDto.builder()
                .id(updatedUser.getId())
                .email(updatedUser.getEmail())
                .firstName(updatedUser.getFirstName())
                .lastName(updatedUser.getLastName())
                .contact(updatedUser.getContact())
                .build();
    }


//    public Void changePassword(String Email){
//        User user = userRepository.findByEmail(Email)
//                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + Email));
//
//    }
}

