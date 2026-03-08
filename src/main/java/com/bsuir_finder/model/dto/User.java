package com.bsuir_finder.model.dto;

import com.bsuir_finder.model.dto.enums.Role;
import com.bsuir_finder.model.dto.enums.UserStatus;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record User(
        @NotNull
        Long id,

        @NotNull
        String email,

        @NotNull
        String username,

        @NotNull
        String password,

        @NotNull
        Role role,

        @NotNull
        LocalDate createdAt,

        UserStatus userStatus
) {
}