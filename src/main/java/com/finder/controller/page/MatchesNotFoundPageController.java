package com.finder.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/matches-not-found")
public class MatchesNotFoundPageController {
    @GetMapping
    public String matchesNotFoundPage() {
        return "matches-not-found";
    }
}
