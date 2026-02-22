package com.bsuir_finder.service;

import com.bsuir_finder.dto.Profile;
import com.bsuir_finder.dto.ProfileView;
import com.bsuir_finder.dto.enums.FormStatus;

import com.bsuir_finder.entity.ProfileViewEntity;
import com.bsuir_finder.mapper.ProfileMapper;
import com.bsuir_finder.mapper.ProfileViewMapper;
import com.bsuir_finder.mapper.UserMapper;
import com.bsuir_finder.repository.ProfileRepository;
import com.bsuir_finder.repository.ProfileViewRepository;
import com.bsuir_finder.security.AuthService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormService {

    private final ProfileService profileService;
    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;
    private final ProfileViewMapper profileViewMapper;
    private final ProfileViewRepository profileViewRepository;
    private final UserMapper userMapper;
    private final AuthService authService;

    public FormService(ProfileService profileService, ProfileRepository profileRepository, ProfileMapper profileMapper, AuthService authService, UserMapper userMapper, ProfileViewMapper profileViewMapper, ProfileViewRepository profileViewRepository) {
        this.profileService = profileService;
        this.profileRepository = profileRepository;
        this.profileMapper = profileMapper;
        this.authService = authService;
        this.userMapper = userMapper;
        this.profileViewMapper = profileViewMapper;
        this.profileViewRepository = profileViewRepository;
    }

    //TODO: maybe transfer to ProfileService
    public List<Profile> allForms() {
        return profileRepository.findAllByFormStatus(FormStatus.ACTIVE)
                .stream()
                .map(profileMapper::toDto)
                .toList();
    }

    public Profile findRandomUnreactedFormById(Long id) {
        var pageable = PageRequest.of(0, 1);
        var randomProfiles = profileViewRepository.findRandomUnreactedWithId(id, pageable);

        if(randomProfiles.isEmpty()) {
            return null;
        }

        return profileMapper.toDto(randomProfiles.getFirst());
    }

    public ProfileView reactionForm(ProfileView profileView) {

        // btn1 btn2
        // if btn1 -> save as like
        // if btn2 -> save as dislike

        var profile = authService.getCurrentUser().getProfile();

        var profileViewToSave = new ProfileViewEntity(
                null,
                profile.getId(),
                profileView.viewedProfileId(),
                profileView.reaction()
        );


        profileViewRepository.save(profileViewToSave);

        return profileViewMapper.toDto(profileViewToSave);
    }
}
