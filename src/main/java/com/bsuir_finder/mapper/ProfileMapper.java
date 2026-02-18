package com.bsuir_finder.mapper;

import com.bsuir_finder.dto.Profile;
import com.bsuir_finder.dto.enums.FormStatus;
import com.bsuir_finder.entity.ProfileEntity;
import org.springframework.stereotype.Component;

@Component
public class ProfileMapper {
    public Profile toDto(ProfileEntity profileEntity) {
        return new Profile(
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
