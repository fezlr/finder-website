package com.bsuir_finder.service;

import com.bsuir_finder.dto.User;
import com.bsuir_finder.repository.UserRepository;

public class UserValidationService {

    private final UserRepository userRepository;

    public UserValidationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validateNewUser(User user) {
        validateUsername(user.username());
        validateEmail(user.email());
    }

    public void validateEmail(String email) {
        if(userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists");
        }
    }

    public void validateUsername(String username){
        if(userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already exists");
        }
    }
}