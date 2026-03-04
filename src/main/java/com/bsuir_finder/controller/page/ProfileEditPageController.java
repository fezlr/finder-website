package com.bsuir_finder.controller.page;

import com.bsuir_finder.security.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("user/profile/edit")
public class ProfileEditPageController {

    private final AuthService authService;

    public ProfileEditPageController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping
    public String profileEditPage(Model model) {
        var profile = authService.getCurrentUser().getProfile();
        model.addAttribute("profile", profile);
        return "profile-edit";
    }
}
