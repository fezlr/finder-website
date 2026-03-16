package com.finder.controller.api;

import com.finder.model.dto.Profile;
import com.finder.model.dto.ProfileView;
import com.finder.security.AuthService;
import com.finder.service.FormService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/api/form")
public class FormApiController {

    private final FormService formService;
    private final AuthService authService;
    private static final Logger log = LoggerFactory.getLogger(FormApiController.class);

    public FormApiController(FormService formService, AuthService authService) {
        this.formService = formService;
        this.authService = authService;
    }

    @GetMapping("/forms")
    public ResponseEntity<List<Profile>> findForms() {
        log.info("Called findAllForms()");
        return ResponseEntity.status(HttpStatus.OK)
                .body(formService.allForms());
    }

    @PostMapping("/react")
    public ResponseEntity<ProfileView> reactionForm(
            @RequestBody ProfileView profileView
    ) {
        log.info("Called reactionForm()");
        log.info("BODY = {}", profileView);
        return ResponseEntity.status(HttpStatus.OK)
                .body(formService.saveForm(profileView.viewedProfileId(), profileView.reaction()));
    }
}