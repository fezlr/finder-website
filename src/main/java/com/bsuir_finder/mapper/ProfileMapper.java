package com.bsuir_finder.mapper;

import com.bsuir_finder.dto.Profile;
import com.bsuir_finder.entity.ProfileEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

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
                profileEntity.getMainPhotoUrl()
        );
    }
}
