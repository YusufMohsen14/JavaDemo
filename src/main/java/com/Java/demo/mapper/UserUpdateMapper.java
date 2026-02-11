package com.Java.demo.mapper;

import com.Java.demo.model.dto.Requests.UserContactDTO;
import com.Java.demo.model.dto.Requests.UserUpdateRequestDTO;
import com.Java.demo.model.entity.User;
import com.Java.demo.model.entity.UserContact;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE)
public interface UserUpdateMapper {

    void updateUser(UserUpdateRequestDTO dto, @MappingTarget User user);

    void updateUserContact(UserContactDTO dto, @MappingTarget UserContact contact);
}
