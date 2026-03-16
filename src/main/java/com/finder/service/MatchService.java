package com.finder.service;

import com.finder.model.dto.Profile;
import com.finder.mapper.ProfileMapper;
import com.finder.repository.ProfileRepository;
import com.finder.repository.ProfileViewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
