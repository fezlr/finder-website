package com.bsuir_finder.controller;

import com.bsuir_finder.service.PhotoStorageService;
import com.bsuir_finder.dto.Profile;
import com.bsuir_finder.service.ProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("api/profile")
public class ProfileApiController {

    private static final Logger log = LoggerFactory.getLogger(ProfileApiController.class);
    private final ProfileService profileService;
    private final PhotoStorageService photoStorageService;

    public ProfileApiController(
            ProfileService profileService,
            PhotoStorageService photoStorageService) {
        this.profileService = profileService;
        this.photoStorageService = photoStorageService;
    }

    @PutMapping
    public ResponseEntity<Profile> updateProfile(
            @Validated @RequestBody Profile profile
    ) {
        log.info("Called updateProfile()");
        return ResponseEntity
                .ok(profileService.updateProfile(profile));
    }

    @PostMapping("/photo")
    public ResponseEntity<?> uploadPhoto(
            @RequestParam("file") MultipartFile file
    ) {
        log.info("Called uploadPhoto()");
        String url = photoStorageService.uploadProfilePhoto(file);
        profileService.updateAndSaveMainPhoto(url);
        return ResponseEntity
                .ok(Map.of("url", url));
    }
}