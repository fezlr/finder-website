package com.finder.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/form-not-found")
public class FormNotFoundPageController {
    @GetMapping
    public String formNotFoundPage() {
        return "form-not-found";
    }
}
