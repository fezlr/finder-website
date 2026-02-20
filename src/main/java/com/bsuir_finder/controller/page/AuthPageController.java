package com.bsuir_finder.controller.page;

import com.bsuir_finder.entity.UserEntity;
import com.bsuir_finder.service.CustomUserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("register")
public class AuthPageController {

    private final CustomUserDetailsService customUserDetailsService;

    public AuthPageController(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @GetMapping
    public String registerPage(
            Model model
    ) {
        model.addAttribute("user", new UserEntity());
        return "register";
    }

    @GetMapping("/confirmToken")
    public String confirmTokenPage(
            @RequestParam String token
    ) {
        customUserDetailsService.confirmToken(token);
        return "confirmToken";
    }
}