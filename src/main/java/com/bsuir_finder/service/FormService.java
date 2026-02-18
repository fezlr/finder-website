package com.bsuir_finder.service;

import com.bsuir_finder.dto.Profile;
import com.bsuir_finder.dto.enums.FormStatus;
import com.bsuir_finder.entity.ProfileEntity;
import com.bsuir_finder.mapper.ProfileMapper;
import com.bsuir_finder.repository.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FormService {

    private final ProfileService profileService;
    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    public FormService(ProfileService profileService, ProfileRepository profileRepository, ProfileMapper profileMapper) {
        this.profileService = profileService;
        this.profileRepository = profileRepository;
        this.profileMapper = profileMapper;
    }

    //TODO: maybe transfer to ProfileService
    public List<Profile> allForms() {
        return profileRepository.findAllByFormStatus(FormStatus.ACTIVE)
                .stream()
                .map(profileMapper::toDto)
                .toList();
    }
}
