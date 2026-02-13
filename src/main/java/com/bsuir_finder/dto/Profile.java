package com.bsuir_finder.dto;

import com.bsuir_finder.dto.enums.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.Period;

public record Profile(
        @NotBlank(message = "{profile.firstname.message.notblank}")
        @Pattern(regexp = "^(?:[А-ЯЁ][а-яё]{1,30}|[A-Z][a-z]{1,30})$",
                 message = "{profile.firstname.pattern}")
        String firstName,

        @NotBlank(message = "{profile.lastname.message.notblank}")
        @Pattern(regexp = "^(?:[А-ЯЁ][а-яё]{1,30}|[A-Z][a-z]{1,30})$",
                 message = "{profile.lastname.message.pattern}")
        String lastName,

        @NotNull(message = "{profile.birthdate.message.notnull")
        @Past(message = "{profile.birthdate.message.past}")
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate birthDate,

        @NotNull(message = "{profile.gender.message.notnull}")
        Gender gender,

        @Size(max = 30,
              message = "{profile.city.message.maxsize}")
        String city,

        @Size(max = 400,
              message = "{profile.aboutme.message.maxsize}")
        String aboutMe,

        @NotNull(message = "{profile.mainphotourl.message.notnull}")
        String mainPhotoUrl,

        String telegramUsername,

        String instagramUsername
) {

    public int age() {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}