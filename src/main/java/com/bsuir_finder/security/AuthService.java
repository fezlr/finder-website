package com.bsuir_finder.security;

import com.bsuir_finder.model.domain.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public UserEntity getCurrentUser() {
        Authentication auth = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new IllegalStateException("User is not authenticated");
        }

        CustomUserDetails details = (CustomUserDetails) auth.getPrincipal();
        return details.getUser();
    }
}