package com.finder.controller.page;

import com.finder.security.AuthService;
import com.finder.service.FormService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/user/form")
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

        var form = formService
                .findRandomUnreactedFormById(
                        authService
                                .getCurrentUser()
                                .getProfile()
                                .getId());

        log.info("BODY = {}", form);
        //TODO: refactor these lines of if-statement
        if (form == null) {
            return "redirect:/user/form-not-found";
        }

        model.addAttribute("form", form);
        return "form";
    }
}
