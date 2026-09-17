package com.example.hotelmanagementsystem.service;

import com.example.hotelmanagementsystem.dto.request.UserRegistrationRequestDTO;
import com.example.hotelmanagementsystem.dto.response.UserResponseDTO;
import com.example.hotelmanagementsystem.entity.User;
import com.example.hotelmanagementsystem.repository.UserRepository;
import com.example.hotelmanagementsystem.util.mapper.UserMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    // using readOnly hibernate skips snapshot creation and dirty checking entirely, saving CPU and heap memory
    @Transactional(readOnly = true)
    public UserResponseDTO findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("User not found with username: " + username));
    }

    @Transactional(readOnly = true)
    public UserResponseDTO findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));
    }

    @Transactional(readOnly = true)
    public List<UserResponseDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDTO)
                .toList();
    }
    @Transactional
    public UserResponseDTO registerUser(UserRegistrationRequestDTO requestDTO) {
        if(userRepository.existsByUsername(requestDTO.username())){
            throw new IllegalArgumentException("Username is already in use: " + requestDTO.username());
        }
        if(userRepository.existsByEmail(requestDTO.email())){
            throw new IllegalArgumentException("Email is already in use: " + requestDTO.email());
        }
        User user = new  User();
        user.setUsername(requestDTO.username());
        user.setEmail(requestDTO.email());
        user.setPassword(requestDTO.password());
        user.setPhone(requestDTO.phone());
        user.setAddress(requestDTO.address());

        return userMapper.toDTO(userRepository.save(user));
    }
}
