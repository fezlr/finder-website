package com.bsuir_finder.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("user/home")
public class HomePageController {
    @GetMapping
    public String homePage() {
        return "home";
    }
}