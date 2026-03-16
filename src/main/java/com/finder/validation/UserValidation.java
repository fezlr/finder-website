package com.finder.validation;

import com.finder.model.dto.User;
import com.finder.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserValidation {
    private final UserRepository userRepository;

    public UserValidation(UserRepository userRepository) {
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

    public void validateUsername(String username) {
        if(userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already exists");
        }
    }
}