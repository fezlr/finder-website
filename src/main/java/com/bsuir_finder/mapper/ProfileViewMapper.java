package com.bsuir_finder.mapper;

import com.bsuir_finder.dto.ProfileView;
import com.bsuir_finder.entity.ProfileViewEntity;
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
