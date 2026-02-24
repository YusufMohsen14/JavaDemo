package com.Java.demo.service;

import com.Java.demo.exception.customException.ResourceNotFoundException;
import com.Java.demo.mapper.PetMapper;
import com.Java.demo.model.dto.Requests.PetRequestDTO;
import com.Java.demo.model.dto.Responses.PetResponseDTO;
import com.Java.demo.model.entity.Pet;
import com.Java.demo.model.entity.User;
import com.Java.demo.model.entity.UserPrincipal;
import com.Java.demo.repository.PetRepository;
import com.Java.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class PetService {

    private final PetRepository petRepository;
    private final UserRepository userRepository;
    private final PetMapper petMapper;

    @Transactional
    public PetResponseDTO createPet(PetRequestDTO request) {
        UserPrincipal userPrincipal = (UserPrincipal) Objects.requireNonNull(
                SecurityContextHolder.getContext().getAuthentication()
        ).getPrincipal();

        User user = userRepository.findByEmail(userPrincipal.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Pet pet = petMapper.toPet(request);
        pet.setUser(user);
        Pet saved = petRepository.save(pet);
        return petMapper.toDto(saved);
    }

    @Transactional
    public PetResponseDTO updatePet(Long petId, PetRequestDTO request) {
        UserPrincipal userPrincipal = (UserPrincipal) Objects.requireNonNull(
                SecurityContextHolder.getContext().getAuthentication()
        ).getPrincipal();
        String email = userPrincipal.getUsername();

        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet not found with ID " + petId));

        if (!pet.getUser().getEmail().equals(email)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not own this pet");
        }

        petMapper.updatePetFromDto(request, pet);
        Pet saved = petRepository.save(pet);
        return petMapper.toDto(saved);
    }

    @Transactional
    public void deletePet(long petId) {
        UserPrincipal userPrincipal = (UserPrincipal) Objects.requireNonNull(
                SecurityContextHolder.getContext().getAuthentication()
        ).getPrincipal();
        String email = userPrincipal.getUsername();

        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet not found with ID " + petId));

        if (!pet.getUser().getEmail().equals(email)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not own this pet");
        }

        petRepository.delete(pet);
    }

    public List<PetResponseDTO> getAllPets() {
        UserPrincipal userPrincipal = (UserPrincipal) Objects.requireNonNull(
                SecurityContextHolder.getContext().getAuthentication()
        ).getPrincipal();
        String email = userPrincipal.getUsername();

        return petRepository.findByUserEmail(email)
                .stream()
                .map(petMapper::toDto)
                .toList();
    }
}
