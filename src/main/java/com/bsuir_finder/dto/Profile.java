package com.bsuir_finder.dto;

import com.bsuir_finder.dto.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;
import java.time.Period;


public record Profile(
        @NotBlank
        @Pattern(regexp = "^(?:[А-ЯЁ][а-яё]{1,30}|[A-Z][a-z]{1,30})$")
        String firstName,

        @NotBlank
        @Pattern(regexp = "^(?:[А-ЯЁ][а-яё]{1,30}|[A-Z][a-z]{1,30})$")
        String lastName,

        @NotNull
        LocalDate birthDate,

        Gender gender,

        String city,

        String aboutMe,

        String mainPhotoUrl
) {

    public int age() {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}
