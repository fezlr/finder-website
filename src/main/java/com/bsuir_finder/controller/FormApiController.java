package com.bsuir_finder.controller;

import com.bsuir_finder.dto.Profile;
import com.bsuir_finder.service.FormService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpResponse;
import java.util.List;

@RestController
@RequestMapping("api/form")
public class FormApiController {

    private final FormService formService;
    private static final Logger log = LoggerFactory.getLogger(FormApiController.class);

    public FormApiController(FormService formService) {
        this.formService = formService;
    }

    @GetMapping("/forms")
    public ResponseEntity<List<Profile>> findAllForms() {
        log.info("Called findAllForms()");
        return ResponseEntity.status(HttpStatus.OK)
                .body(formService.allForms());
    }
}
