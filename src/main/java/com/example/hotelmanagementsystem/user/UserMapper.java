package com.example.hotelmanagementsystem.user;

import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponseDTO toDTO(User user) {
        return new UserResponseDTO(user.getUserId(), user.getUsername(), user.getEmail(), user.getPhone(), user.getAddress());
    }
}
