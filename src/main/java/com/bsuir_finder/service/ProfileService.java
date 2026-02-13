package com.bsuir_finder.service;

import com.bsuir_finder.dto.Profile;
import com.bsuir_finder.dto.enums.FormStatus;
import com.bsuir_finder.entity.ProfileEntity;
import com.bsuir_finder.mapper.ProfileMapper;
import com.bsuir_finder.repository.ProfileRepository;
import com.bsuir_finder.security.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

        var user = authService.getCurrentUser();
        var profileToSave = user.getProfile();

        profileToSave.setFirstName(profileToUpdate.firstName());
        profileToSave.setLastName(profileToUpdate.lastName());
        profileToSave.setBirthDate(profileToUpdate.birthDate());
        profileToSave.setGender(profileToUpdate.gender());
        profileToSave.setCity(profileToUpdate.city());
        profileToSave.setAboutMe(profileToUpdate.aboutMe());
        profileToSave.setTelegramUsername(profileToSave.getTelegramUsername());
        profileToSave.setInstagramUsername(profileToSave.getInstagramUsername());

        if (isProfileEntityComplete(profileToSave)) {
            profileToSave.setVisible(true);
            profileToSave.setFormStatus(FormStatus.ACTIVE);
        }
        else {
            profileToSave.setVisible(false);
            profileToSave.setFormStatus(FormStatus.INCOMPLETE);
        }

        return profileMapper.toDto(profileRepository.save(profileToSave));
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
                .findAllByIsVisibleTrueAndIdNot(currentProfile.getId())
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
                profile.telegramUsername() != null && !profile.telegramUsername().isBlank();
    }

    private boolean isProfileEntityComplete(ProfileEntity profile) {
        return profile.getFirstName() != null && !profile.getFirstName().isBlank() &&
                profile.getLastName() != null && !profile.getLastName().isBlank() &&
                profile.getBirthDate() != null &&
                profile.getGender() != null &&
                profile.getCity() != null && !profile.getCity().isBlank() &&
                profile.getAboutMe() != null && !profile.getAboutMe().isBlank() &&
                profile.getTelegramUsername() != null && !profile.getTelegramUsername().isBlank();
    }
}