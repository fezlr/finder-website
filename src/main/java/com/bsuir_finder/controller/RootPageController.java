package com.bsuir_finder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootPageController {
    @GetMapping("/")
    public String rootPage() {
        return "redirect:/login";
    }
}
