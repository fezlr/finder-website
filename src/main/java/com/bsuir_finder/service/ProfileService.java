package com.bsuir_finder.service;

import com.bsuir_finder.dto.Profile;
import com.bsuir_finder.dto.enums.FormStatus;
import com.bsuir_finder.entity.ProfileEntity;
import com.bsuir_finder.mapper.ProfileMapper;
import com.bsuir_finder.repository.ProfileRepository;
import com.bsuir_finder.security.AuthService;
import com.cloudinary.api.exceptions.BadRequest;
import javassist.tools.web.BadHttpRequest;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class ProfileService {

    private static final Logger log = LoggerFactory.getLogger(ProfileService.class);
    private final AuthService authService;
    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    public ProfileService(AuthService authService, ProfileRepository profileRepository, ProfileMapper profileMapper) {
        this.authService = authService;
        this.profileRepository = profileRepository;
        this.profileMapper = profileMapper;
    }

    @Transactional
    public Profile updateProfile(Profile profileToUpdate) {

        log.info("Called updateProfile()");

        boolean changed = false;

        var profileToSave = authService.getCurrentUser().getProfile();

        if(!Objects.equals(profileToUpdate.firstName(), profileToSave.getFirstName())) {
            profileToSave.setFirstName(profileToUpdate.firstName());
            changed = true;
        }

        if(!Objects.equals(profileToUpdate.lastName(), profileToSave.getLastName())) {
            profileToSave.setLastName(profileToUpdate.lastName());
            changed = true;
        }

        if(!Objects.equals(profileToUpdate.birthDate(), profileToSave.getBirthDate())) {
            profileToSave.setBirthDate(profileToUpdate.birthDate());
            changed = true;
        }

        if(!Objects.equals(profileToUpdate.gender(), profileToSave.getGender())) {
            profileToSave.setGender(profileToUpdate.gender());
            changed = true;
        }

        if(!Objects.equals(profileToUpdate.city(), profileToSave.getCity())) {
            profileToSave.setCity(profileToUpdate.city());
            changed = true;
        }

        if(!Objects.equals(profileToUpdate.aboutMe(), profileToSave.getAboutMe())) {
            profileToSave.setAboutMe(profileToUpdate.aboutMe());
            changed = true;
        }

        if(!Objects.equals(profileToUpdate.telegramUsername(), profileToSave.getTelegramUsername())) {
            profileToSave.setTelegramUsername(profileToUpdate.telegramUsername());
            changed = true;
        }

        if(!Objects.equals(profileToUpdate.instagramUsername(), profileToSave.getInstagramUsername())) {
            profileToSave.setInstagramUsername(profileToUpdate.instagramUsername());
            changed = true;
        }


        if (isProfileEntityComplete(profileToSave)) {
            profileToSave.setFull(true);
        }
        else {
            profileToSave.setFull(false);
        }

        //TODO: direct to another method
        if(Boolean.TRUE.equals(profileToUpdate.formActivation())) {
            if(isProfileEntityComplete(profileToSave)) {
                profileToSave.setFormStatus(FormStatus.ACTIVE);
                changed = true;
            }
            else {
                throw new IllegalArgumentException("Profile is not full");
            }
        }
        else if(Boolean.FALSE.equals(profileToUpdate.formActivation())) {
            profileToSave.setFormStatus(FormStatus.HIDDEN);
            changed = true;
        }

        if(changed) {
            return profileMapper.toDto(profileRepository.save(profileToSave));
        }

        return profileMapper.toDto(profileToSave);

    }

    @Transactional
    public void updateAndSaveMainPhoto(String url) {
        var profile = authService.getCurrentUser().getProfile();
        profile.setMainPhotoUrl(url);
        profileRepository.save(profile);
    }

    @Transactional(readOnly = true)
    public Profile getCurrentProfile() {
        return profileMapper.toDto(authService.getCurrentUser().getProfile());
    }

    @Transactional(readOnly = true)
    public List<Profile> getVisibleForms() {
        var currentProfile = authService.getCurrentUser().getProfile();
        return profileRepository
                .findAllByIsFullTrueAndIdNot(currentProfile.getId())
                .stream()
                .map(profileMapper::toDto)
                .toList();
    }

    public boolean isProfileComplete(Profile profile) {
        return profile.firstName() != null && !profile.firstName().isBlank() &&
                profile.lastName() != null && !profile.lastName().isBlank() &&
                profile.birthDate() != null &&
                profile.gender() != null &&
                profile.city() != null && !profile.city().isBlank() &&
                profile.aboutMe() != null && !profile.aboutMe().isBlank() &&
                ((profile.telegramUsername() != null && !profile.telegramUsername().isBlank()) || (profile.instagramUsername() != null && !profile.instagramUsername().isBlank()));
    }

    private boolean isProfileEntityComplete(ProfileEntity profile) {
        return profile.getFirstName() != null && !profile.getFirstName().isBlank() &&
                profile.getLastName() != null && !profile.getLastName().isBlank() &&
                profile.getBirthDate() != null &&
                profile.getGender() != null &&
                profile.getCity() != null && !profile.getCity().isBlank() &&
                profile.getAboutMe() != null && !profile.getAboutMe().isBlank() &&
                ((profile.getTelegramUsername() != null && !profile.getTelegramUsername().isBlank()) || (profile.getInstagramUsername() != null && !profile.getInstagramUsername().isBlank()));
    }
}