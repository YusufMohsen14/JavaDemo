package com.Java.demo.mapper;

import com.Java.demo.model.dto.Requests.UserRequestDTO;
import com.Java.demo.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserCreateMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "rawPassword", ignore = true)
    @Mapping(target = "refreshToken", ignore = true)
    User toUser(UserRequestDTO dto);
}
