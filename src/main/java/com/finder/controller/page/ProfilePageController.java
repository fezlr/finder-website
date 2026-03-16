package com.finder.controller.page;

import com.finder.security.AuthService;
import com.finder.service.ProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("user/profile")
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
        model.addAttribute("profile", profile);
        model.addAttribute("isComplete", profileService.isProfileComplete(profile));
        return "profile-main";
    }
}