package com.bsuir_finder.controller;

import com.bsuir_finder.security.AuthService;
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

    public ProfilePageController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping
    public String profilePage(Model model) {
        log.info("Called profilePage()");
        var profile = authService.getCurrentUser().getProfile();
        model.addAttribute("profile", profile);
        return "profile-main";
    }
}