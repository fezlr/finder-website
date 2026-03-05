package com.bsuir_finder.controller.page;

import com.bsuir_finder.security.AuthService;
import com.bsuir_finder.service.MatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("/user/matches")
public class MatchPageController {

    private MatchService matchService;
    private AuthService authService;

    public MatchPageController(MatchService matchService, AuthService authService) {
        this.matchService = matchService;
        this.authService = authService;
    }

    @GetMapping
    public String matchPage(@RequestParam(defaultValue = "0") int page, Model model) {
        log.info("Called matchPage()");

        Long id = authService.getCurrentUser().getProfile().getId();
        var matchedForms = matchService.findMatches(id, page);

        //TODO: refactor these lines of if-statement
        if (matchedForms == null || matchedForms.isEmpty()) {
            return "redirect:/user/matches-not-found";
        }

        log.info("BODY = {}", matchedForms);
        log.info("PAGE = {}", page);

        model.addAttribute("forms", matchedForms);
        model.addAttribute("matchesPage", page);

        return "matches";
    }
}
