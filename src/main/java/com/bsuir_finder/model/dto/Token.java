package com.bsuir_finder.model.dto;

import com.bsuir_finder.model.dto.enums.TokenType;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record Token(
        @NotNull
        Long id,

        @NotNull
        String token,

        TokenType tokenType,

        @NotNull
        LocalDateTime createdAt,

        @NotNull
        LocalDateTime expiresAt,

        LocalDateTime confirmedAt,

        @NotNull
        User user
) {
}