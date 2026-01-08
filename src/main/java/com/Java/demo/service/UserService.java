package com.Java.demo.service;

import com.Java.demo.exception.customException.LoginAuthenticationException;
import com.Java.demo.exception.customException.ResourceExistException;
import com.Java.demo.model.dto.Requests.CreateUserDTO;
import com.Java.demo.model.entity.User;
import com.Java.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User createUser(CreateUserDTO createUserDTO) {

        List<User> existUsers = userRepository.findByEmail(createUserDTO.getEmail());
        if (!existUsers.isEmpty()) {
            throw new ResourceExistException("User with email " + createUserDTO.getEmail() + " already exists.");
        }
        User newUser = new User();
        newUser.setFirstName(createUserDTO.getFirstName());
        newUser.setLastName(createUserDTO.getLastName());
        newUser.setEmail(createUserDTO.getEmail());
        newUser.setPassword(createUserDTO.getPassword());
        newUser.setBirthDate(createUserDTO.getBirthDate());
        newUser.setCreatedAt(Instant.now());
        newUser.setUpdatedAt(Instant.now());
        newUser.setRefreshToken("been here");

        return userRepository.save(newUser);
    }

}
