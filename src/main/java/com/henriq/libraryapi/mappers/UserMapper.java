package com.henriq.libraryapi.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.henriq.libraryapi.dto.UserDTO;
import com.henriq.libraryapi.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    
    @Mapping(target = "id", ignore = true)
    User toEntity(UserDTO dto);

    UserDTO toDTO(User user);
}
