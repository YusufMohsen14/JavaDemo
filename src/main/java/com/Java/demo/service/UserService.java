package com.Java.demo.service;

import com.Java.demo.exception.customException.InvalidTokenException;
import com.Java.demo.exception.customException.LoginAuthenticationException;
import com.Java.demo.exception.customException.ResourceExistException;
import com.Java.demo.exception.customException.ResourceNotFoundException;
import com.Java.demo.model.dto.Requests.CreateUserDTO;
import com.Java.demo.model.dto.Requests.UserLoginDTO;
import com.Java.demo.model.dto.Requests.UserRequestDTO;
import com.Java.demo.model.dto.Response.LoginResponseDTO;
import com.Java.demo.model.dto.Response.RefreshTokenResponseDTO;
import com.Java.demo.model.dto.Responses.UserDto;
import com.Java.demo.model.entity.User;
import com.Java.demo.model.entity.UserContact;
import com.Java.demo.repository.UserContactRepository;
import com.Java.demo.repository.UserRepository;
import com.Java.demo.security.JWTUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserContactRepository userContactRepository;
    private final JWTUtil jwtUtil;

    public void createUser(CreateUserDTO createUserDTO) {
        Optional<User> existUsers = userRepository.findByEmail(createUserDTO.getEmail());
        if (existUsers.isPresent()) {
            throw new ResourceExistException("User with email " + createUserDTO.getEmail() + " already exists.");
        }
        User newUser = new User();
        newUser.setFirstName(createUserDTO.getFirstName());
        newUser.setLastName(createUserDTO.getLastName());
        newUser.setEmail(createUserDTO.getEmail());

        newUser.setRawPassword(createUserDTO.getPassword());
        newUser.setPassword(passwordEncoder.encode(createUserDTO.getPassword()));

        newUser.setBirthDate(createUserDTO.getBirthDate());
        newUser.setCreatedAt(Instant.now());
        newUser.setUpdatedAt(Instant.now());

        newUser.setRefreshToken(jwtUtil.generateRefreshToken(newUser));

        UserContact userContact = new UserContact();
        setUserContact(createUserDTO, newUser, userContact);

        newUser.setContact(userContact);
        userRepository.save(newUser);
        userContactRepository.save(userContact);

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

        return com.Java.demo.model.dto.Response.LoginResponseDTO.builder()
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

        return com.Java.demo.model.dto.Response.RefreshTokenResponseDTO.builder()
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

    public void setUserContact(CreateUserDTO createUserDTO, User user, UserContact userContact){
        userContact.setUser(user);
        userContact.setCountry(createUserDTO.getCountry());
        userContact.setCity(createUserDTO.getCity());
        userContact.setPhoneNumber(createUserDTO.getPhoneNumber());
        userContact.setCreatedAt(Instant.now());
        userContact.setUpdatedAt(Instant.now());
    }

    public List<UserDto> getAllUsers() {
        List<User> allUsers =  userRepository.findAll();
        return allUsers.stream().map(user -> UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .contact(userContactRepository.findByUserId(user.getId()).orElseThrow(() -> new ResourceNotFoundException("User contact not found for user id: " + user.getId())))
                .build()).toList();
    }

    public void deleteById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User with id " + id + " not found.");
        }
        userRepository.deleteById(id);
    }

    public UserDto updateUser(UserRequestDTO userRequestDTO) {
        User user = userRepository.findById(userRequestDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + userRequestDTO.getId() + " not found."));

        user.setFirstName(userRequestDTO.getFirstName());
        user.setLastName(userRequestDTO.getLastName());
        user.setEmail(userRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        user.setBirthDate(userRequestDTO.getBirthDate());
        user.setUpdatedAt(Instant.now());

        UserContact userContact = userContactRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User contact not found for user id: " + user.getId()));
        userContact.setCountry(userRequestDTO.getContact().getCountry());
        userContact.setCity(userRequestDTO.getContact().getCity());
        userContact.setPhoneNumber(userRequestDTO.getContact().getPhoneNumber());
        userContact.setUpdatedAt(Instant.now());

        user.setContact(userContact);
        userContactRepository.save(userContact);

        User updatedUser = userRepository.save(user);

        return UserDto.builder()
                .id(updatedUser.getId())
                .email(updatedUser.getEmail())
                .firstName(updatedUser.getFirstName())
                .lastName(updatedUser.getLastName())
                .contact(userContactRepository.findByUserId(updatedUser.getId()).orElseThrow(() -> new ResourceNotFoundException("User contact not found for user id: " + updatedUser.getId())))
                .build();
    }
}

