package com.bsuir_finder.dto;

import com.bsuir_finder.dto.enums.Role;
import com.bsuir_finder.dto.enums.UserStatus;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record User(
        @NotNull
        Long id,

        @NotNull
        Long email,

        @NotNull
        Long password,

        @NotNull
        Role role,

        @NotNull
        LocalDate createdAt,

        UserStatus userStatus
) {
}
