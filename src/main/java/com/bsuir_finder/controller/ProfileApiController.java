package com.bsuir_finder.controller;

import com.bsuir_finder.dto.Profile;
import com.bsuir_finder.service.ProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/profile")
public class ProfileApiController {

    private static final Logger log = LoggerFactory.getLogger(ProfileApiController.class);
    private final ProfileService profileService;

    public ProfileApiController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PutMapping
    public ResponseEntity<Profile> updateProfile(
            @Validated @RequestBody Profile profile
    ) {
        log.info("Called updateProfile()");
        return ResponseEntity
                .ok(profileService.updateProfile(profile));
    }
}