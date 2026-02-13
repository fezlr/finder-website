package com.bsuir_finder.controller;

import com.bsuir_finder.security.AuthService;
import com.bsuir_finder.service.ProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
public class ProfilePageController {
    private static final Logger log = LoggerFactory.getLogger(ProfilePageController.class);
    private final AuthService authService;
    private final ProfileService profileService;

    public ProfilePageController(AuthService authService, ProfileService profileService) {
        this.authService = authService;
        this.profileService = profileService;
    }

    @GetMapping
    public String profilePage(Model model) {
        log.info("Called profilePage()");
        var profile = profileService.getCurrentProfile();
        model.addAttribute("profile", profileService.getCurrentProfile());
        model.addAttribute("isComplete", profileService.isProfileComplete(profile));
        return "profile-main";
    }
}