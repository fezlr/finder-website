package com.bsuir_finder.controller.page;

import com.bsuir_finder.security.AuthService;
import com.bsuir_finder.service.FormService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/form")
public class FormPageController {

    private final FormService formService;
    private final AuthService authService;

    public FormPageController(FormService formService, AuthService authService) {
        this.formService = formService;
        this.authService = authService;
    }

    @GetMapping
    public String formPage(Model model) {
        log.info("Called formPage");

        var profileId = authService.getCurrentUser().getProfile().getId();
        var form = formService.findRandomUnreactedFormById(profileId);
        log.info("BODY = {}", form);
        model.addAttribute("form", form);
        return "form";
    }
}
