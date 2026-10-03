package com.example.hotelmanagementsystem.service;

import com.example.hotelmanagementsystem.dto.request.UserRegistrationRequestDTO;
import com.example.hotelmanagementsystem.dto.response.UserResponseDTO;
import com.example.hotelmanagementsystem.entity.User;
import com.example.hotelmanagementsystem.exception.DuplicateResourceException;
import com.example.hotelmanagementsystem.repository.UserRepository;
import com.example.hotelmanagementsystem.util.mapper.UserMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    // using readOnly hibernate skips snapshot creation and dirty checking entirely, saving CPU and heap memory
    @Transactional(readOnly = true)
    public UserResponseDTO findById(Long id){
        return userRepository.findById(id)
                .map(userMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }
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
    @Transactional(readOnly = true)
    public User findEntityById(Long id) {
        return getUserOrThrow(id);
    }
    @Transactional
    public UserResponseDTO registerUser(UserRegistrationRequestDTO requestDTO) {
        if(userRepository.existsByUsername(requestDTO.username())){
            throw new DuplicateResourceException("Username is already in use: " + requestDTO.username());
        }
        if(userRepository.existsByEmail(requestDTO.email())){
            throw new DuplicateResourceException("Email is already in use: " + requestDTO.email());
        }
        String hashedPassword = passwordEncoder.encode(requestDTO.password());

        User user = new  User();
        user.setUsername(requestDTO.username());
        user.setEmail(requestDTO.email());
        user.setPassword(hashedPassword);
        user.setPhone(requestDTO.phone());
        user.setAddress(requestDTO.address());

        return userMapper.toDTO(userRepository.save(user));
    }
    @Transactional
    public void deactivateUser(Long id) {
        User user = getUserOrThrow(id);
        user.setActive(false);
    }
    @Transactional
    public void deleteUser(Long id) {
        User user = getUserOrThrow(id);
        userRepository.delete(user);
    }
    @Transactional
    public UserResponseDTO reactivateUser(Long id) {
        User user = getUserOrThrow(id);
        user.setActive(true);
        return userMapper.toDTO(user);
    }
    @Transactional
    public UserResponseDTO updateProfile(Long id, UserRegistrationRequestDTO requestDTO) {
        User user = getUserOrThrow(id);
        if (requestDTO.email() != null && !requestDTO.email().equals(user.getEmail())) {
            if (userRepository.existsByEmail(requestDTO.email())) {
                throw new DuplicateResourceException("Email is already in use: " + requestDTO.email());
            }
            user.setEmail(requestDTO.email());
        }
        if (requestDTO.phone() != null) {
            user.setPhone(requestDTO.phone());
        }
        if (requestDTO.address() != null) {
            user.setAddress(requestDTO.address());
        }

        return userMapper.toDTO(user);
    }
    private User getUserOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }
}
