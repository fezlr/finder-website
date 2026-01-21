package com.bsuir_finder.dto;

import com.bsuir_finder.dto.enums.TokenType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

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