package com.bsuir_finder.controller;

import com.bsuir_finder.dto.Profile;
import com.bsuir_finder.service.FormService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/form")
public class FormPageController {

    private final FormService formService;

    public FormPageController(FormService formService) {
        this.formService = formService;
    }

    @GetMapping
    public String formPage(Model model) {
        model.addAttribute("profiles", formService.allForms());
        return "form";
    }
}
