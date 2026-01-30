package com.bsuir_finder.service;

import com.bsuir_finder.dto.Profile;
import com.bsuir_finder.mapper.ProfileMapper;
import com.bsuir_finder.repository.ProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfileService {

    private static final Logger log = LoggerFactory.getLogger(ProfileService.class);
    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    public ProfileService(ProfileRepository profileRepository, ProfileMapper profileMapper) {
        this.profileRepository = profileRepository;
        this.profileMapper = profileMapper;
    }

    @Transactional
    public Profile updateProfile(Profile profileToUpdate) {
        log.info("Called updateProfile()");

        var entityProfileToSave = profileRepository.findById(profileToUpdate.)
                .orElseThrow(() ->
                        new IllegalArgumentException("Profile not found"));

        profileRepository.save(entityProfileToSave);
        return profileMapper.toDto(entityProfileToSave);
    }
}