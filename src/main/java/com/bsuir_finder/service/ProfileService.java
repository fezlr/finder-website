package com.bsuir_finder.service;

import com.bsuir_finder.dto.Profile;
import com.bsuir_finder.mapper.ProfileMapper;
import com.bsuir_finder.repository.ProfileRepository;
import com.bsuir_finder.security.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        return profileMapper.toDto(profileRepository.save(profileToSave));
    }

    @Transactional
    public void updateAndSaveMainPhoto(String url) {
        var profile = authService.getCurrentUser().getProfile();
        profile.setMainPhotoUrl(url);
        profileRepository.save(profile);
    }
}