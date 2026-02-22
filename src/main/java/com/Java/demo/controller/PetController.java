package com.Java.demo.controller;

import com.Java.demo.mapper.PetMapper;
import com.Java.demo.model.dto.Requests.PetRequestDTO;
import com.Java.demo.model.dto.Responses.PetResponseDTO;
import com.Java.demo.model.entity.Pet;
import com.Java.demo.service.PetService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pets")
@AllArgsConstructor
public class PetController {

    private final PetService petService;
    private final PetMapper petMapper;

    @PostMapping("/users/{userId}/pets")
    public ResponseEntity<PetResponseDTO> createPet(@PathVariable Long userId, @Valid @RequestBody PetRequestDTO request) {

        Pet pet = petMapper.toPet(request);
        Pet savedPet = petService.addPet(userId, pet);

        PetResponseDTO response = new PetResponseDTO(
                savedPet.getId(),
                savedPet.getName(),
                savedPet.getBirthDate(),
                savedPet.getType(),
                savedPet.getCreatedAt()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
