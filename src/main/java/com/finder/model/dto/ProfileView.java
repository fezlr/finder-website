package com.finder.model.dto;

import com.finder.model.dto.enums.Reaction;

public record ProfileView(
        Long id,

        Long viewerId,

        Long viewedProfileId,

        Reaction reaction
) {
}
