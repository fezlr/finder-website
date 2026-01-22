package com.bsuir_finder.controller;

import com.bsuir_finder.entity.UserEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("register")
public class AuthPageController {

    @GetMapping
    public String registerPage(
            Model model
    ) {
        model.addAttribute("user", new UserEntity());
        return "register";
    }

    @GetMapping("/confirmToken")
    public String confirmTokenPage() {
        return "confirmToken";
    }
}
