package com.finder.mapper;

import com.finder.model.dto.Profile;
import com.finder.model.dto.enums.FormStatus;
import com.finder.model.domain.ProfileEntity;
import org.springframework.stereotype.Component;

@Component
public class ProfileMapper {
    public Profile toDto(ProfileEntity profileEntity) {
        return new Profile(
                profileEntity.getId(),
                profileEntity.getFirstName(),
                profileEntity.getLastName(),
                profileEntity.getBirthDate(),
                profileEntity.getGender(),
                profileEntity.getCity(),
                profileEntity.getAboutMe(),
                profileEntity.getMainPhotoUrl(),
                profileEntity.getTelegramUsername(),
                profileEntity.getInstagramUsername(),
                formState(profileEntity)
        );
    }

    private Boolean formState(ProfileEntity profileEntity) {
        if(profileEntity.getFormStatus() == FormStatus.ACTIVE) {
            return true;
        }
        return false;
    }
}
