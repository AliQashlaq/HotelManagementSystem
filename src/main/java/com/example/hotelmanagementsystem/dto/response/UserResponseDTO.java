package com.example.hotelmanagementsystem.dto.response;

public record UserResponseDTO(
        Long userId,
        String username,
        String email,
        String phone,
        String address
) {
}
