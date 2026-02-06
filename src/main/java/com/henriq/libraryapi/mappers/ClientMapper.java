package com.henriq.libraryapi.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.henriq.libraryapi.dto.ClientDTO;
import com.henriq.libraryapi.model.Client;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    
    ClientDTO toDTO(Client client);

    @Mapping(target = "id", ignore = true)
    Client toEntity(ClientDTO clientDTO);
}
