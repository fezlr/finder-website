package com.bsuir_finder.controller.page;

import com.bsuir_finder.security.AuthService;
import com.bsuir_finder.service.FormService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        var profileId = authService.getCurrentUser().getProfile().getId();
        var form = formService.findNextFormById(profileId);
        model.addAttribute("form", form);
        return "form";
    }
}
