package com.Java.demo.mapper;

import com.Java.demo.model.dto.Requests.PetDTO;
import com.Java.demo.model.entity.Pet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public class PetMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    Pet toPet(PetDTO petDTO);
}
