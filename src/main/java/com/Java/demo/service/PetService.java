package com.Java.demo.service;

import com.Java.demo.model.entity.Pet;
import com.Java.demo.model.entity.User;
import com.Java.demo.repository.PetRepository;
import com.Java.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PetService {

    private final PetRepository petRepository;
    private final UserRepository userRepository;

    public Pet addPet(Long userId, Pet pet) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        pet.setUser(user);
        return petRepository.save(pet);
    }

    public Pet getPetById(Long petId) {
        return petRepository.findById(petId)
                .orElseThrow(() -> new RuntimeException("Pet not found"));
    }

    public List<Pet> getPetsByUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found");
        }
        return petRepository.findByUserId(userId);
    }

    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    public void deletePetById(Long petId) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new RuntimeException("Pet not found"));

        petRepository.delete(pet);
    }
}
