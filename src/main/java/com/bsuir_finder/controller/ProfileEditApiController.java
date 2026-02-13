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
@RequestMapping("api/edit")
public class ProfileEditApiController {

    private static final Logger log = LoggerFactory.getLogger(ProfileEditApiController.class);
    private final ProfileService profileService;
    private final PhotoStorageService photoStorageService;

    public ProfileEditApiController(
            ProfileService profileService,
            PhotoStorageService photoStorageService) {
        this.profileService = profileService;
        this.photoStorageService = photoStorageService;
    }

    @PutMapping
    public ResponseEntity<Profile> updateProfile(
            @RequestBody Profile profile
    ) {
        log.info("Called updateProfile with body = {}", profile);
        return ResponseEntity
                .ok(profileService.updateProfile(profile));
    }

    @PostMapping("/photo")
    public ResponseEntity<Map<String, String>> uploadPhoto(
            @Validated @RequestParam("file") MultipartFile file
    ) {
        log.info("Called uploadPhoto()");
        String url = photoStorageService.uploadProfilePhoto(file);
        profileService.updateAndSaveMainPhoto(url);
        return ResponseEntity
                .ok(Map.of("url", url));
    }
}