package com.bsuir_finder.dto;

import com.bsuir_finder.dto.enums.Reaction;

public record ProfileView(
        Long id,

        User viewer,

        Profile viewedProfile,

        Reaction reaction
) {
}
