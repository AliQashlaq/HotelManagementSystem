package com.example.hotelmanagementsystem.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(name = "ApiError", description = "Standard error body returned by the global exception handler.")
public record ErrorResponseDTO(
        @Schema(description = "Readable human explanation of the error.", example = "User not found with id: 5")
        String message,

        @Schema(description = "The HTTP status code.", example = "404")
        int statusCode,

        @Schema(description = "Timestamp when the error occurred.", example = "2026-09-19T11:00:00")
        LocalDateTime timestamp
) {
    public ErrorResponseDTO(String message, int statusCode) {
        this(message, statusCode, LocalDateTime.now());
    }
}
