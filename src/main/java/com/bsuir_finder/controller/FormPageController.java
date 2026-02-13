package com.bsuir_finder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/form")
public class FormPageController {
    @GetMapping
    public String formPage() {
        return "form";
    }
}
