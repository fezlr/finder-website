package com.finder.mapper;

import com.finder.model.dto.ProfileView;
import com.finder.model.domain.ProfileViewEntity;
import org.springframework.stereotype.Component;

@Component
public class ProfileViewMapper {
    public ProfileView toDto(ProfileViewEntity profileViewEntity) {
        return new ProfileView(
                profileViewEntity.getId(),
                profileViewEntity.getViewerId(),
                profileViewEntity.getViewedProfileId(),
                profileViewEntity.getReaction()
        );
    }
}
