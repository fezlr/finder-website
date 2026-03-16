package com.finder.controller.page;

import com.finder.model.dto.Profile;
import com.finder.security.AuthService;
import com.finder.service.MatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/matches")
public class MatchPageController {

    private final Logger log = LoggerFactory.getLogger(MatchPageController.class);

    private MatchService matchService;
    private AuthService authService;

    public MatchPageController(MatchService matchService, AuthService authService) {
        this.matchService = matchService;
        this.authService = authService;
    }

    @GetMapping
    public String matchPage(@PageableDefault(size = 20) Pageable pageable, Model model) {
        log.info("Called matchPage()");
        Long id = authService.getCurrentUser().getProfile().getId();
        Page<Profile> matchedForms = matchService.findMatches(id, pageable);

        if (matchedForms.getTotalElements() == 0) {
            return "redirect:/user/matches-not-found";
        }

        log.info("BODY = {}", matchedForms);
        log.info("PAGE = {}", pageable);
        model.addAttribute("forms", matchedForms);

        return "matches";
    }
}
