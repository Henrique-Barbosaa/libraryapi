package com.henriq.libraryapi.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.henriq.libraryapi.dto.UserDTO;
import com.henriq.libraryapi.mappers.UserMapper;
import com.henriq.libraryapi.model.User;
import com.henriq.libraryapi.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

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
        
        userRepository.save(user);
    }

    public User getByLogin(String username){
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
    }

    public User getByEmail(String email){
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
    }
}
