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

@RestController
@RequestMapping("/pets")
@AllArgsConstructor
public class PetController {

    private final PetService petService;

    @PostMapping
    public ResponseEntity<PetResponseDTO> createPet(@RequestBody @Valid PetRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(petService.createPet(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetResponseDTO> updatePet(@PathVariable Long id, @RequestBody @Valid PetRequestDTO request) {
        return ResponseEntity.ok(petService.updatePet(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable Long id) {
        petService.deletePet(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<PetResponseDTO>> getAllPets() {
        return ResponseEntity.ok(petService.getAllPets());
    }

}
