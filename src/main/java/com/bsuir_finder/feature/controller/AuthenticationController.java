package com.bsuir_finder.feature.controller;

import com.bsuir_finder.feature.entity.UserInfoEntity;
import com.bsuir_finder.feature.service.CustomUserDetailsService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// User == UserInfoEntity (tutor)

@RestController
@RequestMapping("register")
public class AuthenticationController {

    private final CustomUserDetailsService userDetailsService;

    public AuthenticationController(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping
    public String register(Model model) {
        UserInfoEntity user = new UserInfoEntity();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping
    public String postUser(
            @ModelAttribute("user") UserInfoEntity user,
            Model model,
            RedirectAttributes redirectAttributes) {

        // save the user to database
        // send the confirmation email

        userDetailsService.registerUser(user);
        redirectAttributes.addFlashAttribute(
                "message",
                "Please confirm your email address"

        );
        return "redirect:/register";
    }


    public String confirmToken(
            @RequestParam("token") String token,
            Model model
    ) {
        userDetailsService.confirmToken(token);
        return "confirmToken";
    }

}
