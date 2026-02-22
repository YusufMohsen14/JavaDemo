package com.Java.demo.controller;

import com.Java.demo.mapper.PetMapper;
import com.Java.demo.model.dto.Requests.PetDTO;
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

    @PostMapping("/user/{userId}")
    public ResponseEntity<Pet> createPet(@PathVariable Long userId, @Valid @RequestBody PetDTO petDTO) {

        Pet pet = petMapper.toPet(petDTO);
        Pet savedPet = petService.addPet(userId, pet);

        return ResponseEntity.ok(savedPet);
    }

    @GetMapping("/{petId}")
    public ResponseEntity<Pet> getPetById(@PathVariable Long petId) {
        return ResponseEntity.ok(petService.getPetById(petId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Pet>> getPetsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(petService.getPetsByUser(userId));
    }

    @GetMapping
    public ResponseEntity<List<Pet>> getAllPets() {
        return ResponseEntity.ok(petService.getAllPets());
    }

    @DeleteMapping("/{petId}")
    public ResponseEntity<Void> deletePet(@PathVariable Long petId) {
        petService.deletePetById(petId);
        return ResponseEntity.noContent().build();
    }
}
