package com.finder.controller.api;

import com.finder.model.dto.Token;
import com.finder.model.dto.User;
import com.finder.service.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/api/auth")
public class AuthApiController {

    private static final Logger log = LoggerFactory.getLogger(AuthApiController.class);
    private final CustomUserDetailsService customUserDetailsService;

    public AuthApiController(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(
            @RequestBody User user
    ) {
        log.info("Called registerUser()");
        log.info("BODY = {}", user);
         return ResponseEntity
                 .ok(customUserDetailsService.registerUser(user));
    }

    @GetMapping("/confirmToken")
    public ResponseEntity<Token> confirmToken(
            @RequestParam String token
    ) {
        log.info("Called confirmToken()");
        customUserDetailsService.confirmToken(token);
        return ResponseEntity.ok().build();
    }
}