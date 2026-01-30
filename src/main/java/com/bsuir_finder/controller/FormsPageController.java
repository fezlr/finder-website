package com.bsuir_finder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/forms")
public class FormsPageController {
    @GetMapping
    public String formsPage() {
        return "forms";
    }
}
