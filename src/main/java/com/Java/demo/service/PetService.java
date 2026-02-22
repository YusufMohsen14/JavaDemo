package com.Java.demo.service;

import com.Java.demo.exception.customException.ResourceNotFoundException;
import com.Java.demo.model.entity.Pet;
import com.Java.demo.model.entity.User;
import com.Java.demo.repository.PetRepository;
import com.Java.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class PetService {

    private final PetRepository petRepository;
    private final UserRepository userRepository;

    @Transactional
    public Pet addPet(Long userId, Pet pet) {
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + userId));
        pet.setUser(user);
        return petRepository.save(pet);
    }
}
