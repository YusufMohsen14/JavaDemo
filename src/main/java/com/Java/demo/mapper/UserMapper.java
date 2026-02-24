package com.Java.demo.mapper;

import com.Java.demo.model.dto.Requests.UserContactDTO;
import com.Java.demo.model.dto.Requests.UserRequestDTO;
import com.Java.demo.model.dto.Requests.UserUpdateRequestDTO;
import com.Java.demo.model.entity.User;
import com.Java.demo.model.entity.UserContact;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "rawPassword", ignore = true)
    @Mapping(target = "refreshToken", ignore = true)
    @Mapping(target = "role", ignore = true)
    User toUser(UserRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    UserContact toUserContact(UserContactDTO dto);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rawPassword", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "refreshToken", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "role", ignore = true)
    void updateUser(UserUpdateRequestDTO dto, @MappingTarget User user);


    @Mapping(target = "user", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateUserContact(UserContactDTO dto, @MappingTarget UserContact contact);

}