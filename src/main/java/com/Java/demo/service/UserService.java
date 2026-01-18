package com.Java.demo.service;


import com.Java.demo.exception.customException.LoginAuthenticationException;
import com.Java.demo.exception.customException.ResourceExistException;
import com.Java.demo.model.dto.Requests.CreateUserDTO;
import com.Java.demo.model.dto.Requests.UserLoginDTO;
import com.Java.demo.model.entity.User;
import com.Java.demo.model.entity.UserContact;
import com.Java.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;

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
        newUser.setRefreshToken("been here");

        UserContact userContact = new UserContact();
        userContact.setUser(newUser);
        userContact.setCountry(createUserDTO.getCountry());
        userContact.setCity(createUserDTO.getCity());
        userContact.setPhoneNumber(createUserDTO.getPhoneNumber());
        userContact.setCreatedAt(Instant.now());
        userContact.setUpdatedAt(Instant.now());

        newUser.setContact(userContact);
        userRepository.save(newUser);
    }

    public User loginUser(UserLoginDTO dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new LoginAuthenticationException("Invalid email or password"));
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new LoginAuthenticationException("Invalid email or password");
        }
        return user;
    }
}
