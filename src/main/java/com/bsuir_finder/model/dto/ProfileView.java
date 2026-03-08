package com.bsuir_finder.model.dto;

import com.bsuir_finder.model.dto.enums.Reaction;

public record ProfileView(
        Long id,

        Long viewerId,

        Long viewedProfileId,

        Reaction reaction
) {
}
