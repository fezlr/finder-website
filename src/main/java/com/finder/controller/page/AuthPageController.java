package com.finder.controller.page;

import com.finder.model.domain.UserEntity;
import com.finder.repository.UserRepository;
import com.finder.service.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user/register")
public class AuthPageController {
    private static final Logger log = LoggerFactory.getLogger(AuthPageController.class);
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
        log.info("Called CONFIRMTOKENPAGE");
        customUserDetailsService.confirmToken(token);
        return "confirmToken";
    }
}