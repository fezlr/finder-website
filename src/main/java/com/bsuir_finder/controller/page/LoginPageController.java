package com.bsuir_finder.controller.page;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginPageController {
    private static final Logger log = LoggerFactory.getLogger(LoginPageController.class);

    @GetMapping
    public String loginPage() {
        log.info("Called loginPage()");
        return "login";
    }
}