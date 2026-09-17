package com.example.hotelmanagementsystem.util.mapper;

import com.example.hotelmanagementsystem.dto.response.UserResponseDTO;
import com.example.hotelmanagementsystem.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponseDTO toDTO(User user) {
        return new UserResponseDTO(user.getUserId(), user.getUsername(), user.getEmail(), user.getPhone(), user.getAddress());
    }
}
