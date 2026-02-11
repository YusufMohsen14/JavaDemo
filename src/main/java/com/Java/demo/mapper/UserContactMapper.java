package com.Java.demo.mapper;

import com.Java.demo.model.dto.Requests.UserContactDTO;
import com.Java.demo.model.entity.UserContact;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserContactMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    UserContact toUser(UserContactDTO dto);

}
