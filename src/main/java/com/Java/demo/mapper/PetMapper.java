package com.Java.demo.mapper;

import com.Java.demo.model.dto.Requests.PetRequestDTO;
import com.Java.demo.model.dto.Responses.PetResponseDTO;
import com.Java.demo.model.entity.Pet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PetMapper {

    @Mapping(target = "userId", source = "user.id")
    PetResponseDTO toDto(Pet pet);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Pet toPet(PetRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updatePetFromDto(PetRequestDTO dto, @MappingTarget Pet pet);

}