package com.henriq.libraryapi.service;

import com.henriq.libraryapi.dto.UserUpdateDTO;
import com.henriq.libraryapi.model.Roles;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.henriq.libraryapi.dto.UserDTO;
import com.henriq.libraryapi.mappers.UserMapper;
import com.henriq.libraryapi.model.User;
import com.henriq.libraryapi.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder encoder;

    UserService(UserRepository userRepository, PasswordEncoder encoder, UserMapper userMapper){
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.userMapper = userMapper;
    }

    public void save(UserDTO dto){
        var password = encoder.encode(dto.password());
        User user = userMapper.toEntity(dto);
        user.setPassword(password);
        user.setRoles(List.of(Roles.USER));
        userRepository.save(user);
    }

    public User getByEmail(String email){
        return userRepository.findByEmail(email)
            .orElse(null);
    }

    public void update(UserUpdateDTO userDTO, String id) {
        User user = userRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        user.setEmail(userDTO.email());
        user.setPassword(encoder.encode(userDTO.password()));
        user.setRoles(userDTO.roles());
        userRepository.save(user);
    }

}
