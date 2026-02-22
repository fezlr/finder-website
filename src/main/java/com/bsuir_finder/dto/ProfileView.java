package com.bsuir_finder.dto;

import com.bsuir_finder.dto.enums.Reaction;

public record ProfileView(
        Long id,

        Long viewerId,

        Long viewedProfileId,

        Reaction reaction
) {
}
