package com.bsuir_finder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile/edit")
public class ProfileEditPageController {
    @GetMapping
    public String profileEditPage() {
        return "profile-edit";
    }
}
