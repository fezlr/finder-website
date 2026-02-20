package com.bsuir_finder.service;

import com.bsuir_finder.dto.Profile;
import com.bsuir_finder.dto.ProfileView;
import com.bsuir_finder.dto.enums.FormStatus;

import com.bsuir_finder.entity.ProfileViewEntity;
import com.bsuir_finder.mapper.ProfileMapper;
import com.bsuir_finder.repository.ProfileRepository;
import com.bsuir_finder.security.AuthService;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormService {

    private final ProfileService profileService;
    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;
    private final AuthService authService;

    public FormService(ProfileService profileService, ProfileRepository profileRepository, ProfileMapper profileMapper, AuthService authService) {
        this.profileService = profileService;
        this.profileRepository = profileRepository;
        this.profileMapper = profileMapper;
        this.authService = authService;
    }

    //TODO: maybe transfer to ProfileService
    public List<Profile> allForms() {
        return profileRepository.findAllByFormStatus(FormStatus.ACTIVE)
                .stream()
                .map(profileMapper::toDto)
                .toList();
    }

    public Profile findNextFormById(Long id) {
        var randomProfile = profileRepository.findRandomProfileWithIdNot(id);
        return profileMapper.toDto(randomProfile);
    }

    public ProfileView reactionForm(ProfileView profileView) {

        var profile = authService.getCurrentUser().getProfile();
        var profileViewToSave = new ProfileViewEntity(
                null,
                profileView.viewer(),
                profileView.viewedProfile(),
                profileView.reaction()
        );

    }
}
