package com.bsuir_finder.dto;

import com.bsuir_finder.dto.enums.Role;
import com.bsuir_finder.dto.enums.UserStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

import java.time.LocalDate;

public record User(
        @NotNull
        Long id,

        @NotNull
        String email,

        @NotNull
        String username,

        @NotNull
        String firstName,

        @NotNull
        String lastName,

        @NotNull
        String password,

        @NotNull
        Role role,

        @NotNull
        LocalDate createdAt,

        UserStatus userStatus
) {
}