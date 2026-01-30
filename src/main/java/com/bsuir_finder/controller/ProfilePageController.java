package com.bsuir_finder.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
public class ProfilePageController {
    private static final Logger log = LoggerFactory.getLogger(ProfilePageController.class);

    @GetMapping
    public String profilePage() {
        log.info("Called profilePage()");
        return "profile";
    }
}
