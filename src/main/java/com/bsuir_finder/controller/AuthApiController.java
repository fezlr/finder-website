package com.bsuir_finder.controller;

import com.bsuir_finder.dto.Token;
import com.bsuir_finder.dto.User;
import com.bsuir_finder.entity.TokenEntity;
import com.bsuir_finder.entity.UserEntity;
import com.bsuir_finder.service.CustomUserDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public class AuthApiController {

    private final CustomUserDetailsService customUserDetailsService;

    public AuthApiController(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(
            @RequestBody User user
    ) {
         return ResponseEntity.ok()
                 .body(customUserDetailsService.registerUser(user));
    }

    @GetMapping("/confirmToken")
    public ResponseEntity<Token> confirmToken(
            @RequestParam String token
    ) {
        customUserDetailsService.confirmToken(token);
        return ResponseEntity.ok().build();
    }
}
