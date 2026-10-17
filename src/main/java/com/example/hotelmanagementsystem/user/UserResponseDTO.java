package com.example.hotelmanagementsystem.user;

public record UserResponseDTO(
        Long userId,
        String username,
        String email,
        String phone,
        String address
) {
}
