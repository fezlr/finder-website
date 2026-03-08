package com.bsuir_finder.service;

import com.bsuir_finder.model.domain.ProfileEntity;
import com.bsuir_finder.model.dto.Profile;
import com.bsuir_finder.mapper.ProfileMapper;
import com.bsuir_finder.repository.ProfileRepository;
import com.bsuir_finder.repository.ProfileViewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchService {

    private final ProfileViewRepository profileViewRepository;
    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    public MatchService(ProfileViewRepository profileViewRepository, ProfileMapper profileMapper, ProfileRepository profileRepository) {
        this.profileViewRepository = profileViewRepository;
        this.profileMapper = profileMapper;
        this.profileRepository = profileRepository;
    }

    public Page<Profile> findMatches(Long id, Pageable pageable) {
        return profileViewRepository
                .findMutualLikes(id, pageable)
                .map(profileMapper::toDto);
    }
}
