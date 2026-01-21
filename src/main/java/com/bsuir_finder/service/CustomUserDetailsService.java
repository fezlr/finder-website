package com.bsuir_finder.service;

import com.bsuir_finder.dto.enums.Role;
import com.bsuir_finder.entity.TokenEntity;
import com.bsuir_finder.entity.UserEntity;
import com.bsuir_finder.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final EmailService emailService;

    public CustomUserDetailsService(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenService tokenService, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
        this.emailService = emailService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public UserEntity registerUser(UserEntity user) {
        userRepository.findByUsernameOrEmail(user.getUsername(), user.getEmail())
                .ifPresent(
                        existingUser -> {
                            throw new IllegalStateException("User already exist");
                        }
                );
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        user.setCreatedAt(LocalDate.now());
        user.setRole(Role.USER);
        userRepository.save(user);

        TokenEntity confirmationToken = new TokenEntity(
                UUID.randomUUID().toString(),
                null,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
        );
        tokenService.save(confirmationToken);

        emailService.send(user.getEmail(), confirmationToken.getToken());

        return user;
    }

    public void confirmToken(String token) {
        TokenEntity confirmedToken = tokenService.findByToken(token)
                .orElseThrow(
                        () -> new IllegalArgumentException("Token not found")
                );
        if (confirmedToken.getConfirmedAt() != null) {
            throw new IllegalArgumentException("User already verified");
        }

        if (confirmedToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Token expired");
        }

        confirmedToken.setConfirmedAt(LocalDateTime.now());
        tokenService.save(confirmedToken);
        enableUser(confirmedToken.getUser());
    }

    private void enableUser(UserEntity user) {
        user.setEnabled(true);
        userRepository.save(user);
    }
}