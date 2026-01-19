package com.bsuir_finder.controller;

import com.bsuir_finder.dto.User;
import com.bsuir_finder.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final static Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    
    @GetMapping("/{id}")
    public ResponseEntity<User> findUserById(
            @PathVariable Long id
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.findUserById(id));
    }

    @GetMapping
    public ResponseEntity<List<User>> findAllUsers() {
        log.info("Called findAllUsers() method");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.findAllUsers());
    }

    @PostMapping
    public ResponseEntity<User> createUser(
            @RequestBody @Valid User UserToCreate
    ) {
        log.info("Called createUser() method");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.createUser(UserToCreate));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUserById(
            @PathVariable("id") Long id,
            @RequestBody @Valid User UserToUpdate
    ) {
        log.info("Called updateUserById() method id: {}", id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.updateUserById(id, UserToUpdate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(
            @PathVariable("id") Long id
    ) {
        log.info("Called deleteUserById() method");
        userService.cancelUserById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}