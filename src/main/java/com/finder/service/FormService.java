package com.finder.service;

import com.finder.model.dto.Profile;
import com.finder.model.dto.ProfileView;
import com.finder.model.dto.enums.FormStatus;

import com.finder.model.dto.enums.Reaction;
import com.finder.model.domain.ProfileViewEntity;
import com.finder.mapper.ProfileMapper;
import com.finder.mapper.ProfileViewMapper;
import com.finder.mapper.UserMapper;
import com.finder.repository.ProfileRepository;
import com.finder.repository.ProfileViewRepository;
import com.finder.security.AuthService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        var randomProfile = profileViewRepository.findRandomUnreactedWithId(id, pageable);

        if(randomProfile.isEmpty()) {
            return null;
        }

        return profileMapper.toDto(randomProfile.getFirst());
    }

    @Transactional
    public ProfileView saveForm(Long viewedId, Reaction reaction) {

        var entityViewedProfile = profileRepository
                .findById(viewedId)
                .orElseThrow(() -> new IllegalStateException("Profile not found"));

        boolean reacted = profileViewRepository.existsByViewerIdAndViewedProfileId(
                authService.getCurrentUser().getId(),
                viewedId
        );

        if(reacted) {
            throw new IllegalStateException("You have already reacted");
        }

        var entityToSave = new ProfileViewEntity(
                null,
                authService.getCurrentUser().getId(),
                viewedId,
                reaction
        );

        profileViewRepository.save(entityToSave);

        return profileViewMapper.toDto(entityToSave);
    }
}
