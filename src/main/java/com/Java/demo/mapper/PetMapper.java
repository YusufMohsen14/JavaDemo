package com.Java.demo.mapper;

import com.Java.demo.model.dto.Requests.PetRequestDTO;
import com.Java.demo.model.entity.Pet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PetMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    Pet toPet(PetRequestDTO petDTO);
}
