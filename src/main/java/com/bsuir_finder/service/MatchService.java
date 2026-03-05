package com.bsuir_finder.service;

import com.bsuir_finder.dto.Profile;
import com.bsuir_finder.mapper.ProfileMapper;
import com.bsuir_finder.repository.ProfileViewRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchService {

    private final ProfileViewRepository profileViewRepository;
    private final ProfileMapper profileMapper;

    public MatchService(ProfileViewRepository profileViewRepository, ProfileMapper profileMapper) {
        this.profileViewRepository = profileViewRepository;
        this.profileMapper = profileMapper;
    }

    public List<Profile> findMatches(Long id, int page) {
        Pageable pageable = PageRequest.of(page, 20);
        return profileViewRepository
                .findMutualLikes(id, pageable)
                .stream()
                .map(profileMapper::toDto)
                .toList();
    }
}
