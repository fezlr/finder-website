package com.bsuir_finder.controller.page;

import com.bsuir_finder.model.domain.UserEntity;
import com.bsuir_finder.model.dto.User;
import com.bsuir_finder.repository.UserRepository;
import com.bsuir_finder.service.CustomUserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user/register")
public class AuthPageController {
    private final CustomUserDetailsService customUserDetailsService;
    private final UserRepository userRepository;

    public AuthPageController(CustomUserDetailsService customUserDetailsService, UserRepository userRepository) {
        this.customUserDetailsService = customUserDetailsService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String registerPage(Model model) {
        model.addAttribute("user", new UserEntity());
        return "register";
    }

    @GetMapping("/confirmToken")
    public String confirmTokenPage(@RequestParam String token) {
        customUserDetailsService.confirmToken(token);
        return "confirmToken";
    }
}