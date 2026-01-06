package com.Java.demo.service;

import com.Java.demo.model.entity.User;
import com.Java.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserService {

    //dependency injection
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(String firstName, String lastName, String email, Date birthDate, String password){
        User newUser = new User();
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setEmail(email);
        newUser.setBirthDate(birthDate);

        newUser.setCreatedAt(java.time.Instant.now());
        return userRepository.save(newUser);
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id){
        return userRepository.findById(id).orElse(null);
    }

    public Boolean deleteUserById(Long id){
        if (userRepository.existsById(id)){
            userRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public long countUsers(){
        return userRepository.count();
    }

}
