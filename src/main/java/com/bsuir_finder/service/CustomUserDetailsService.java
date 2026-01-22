package com.bsuir_finder.service;

import com.bsuir_finder.dto.User;
import com.bsuir_finder.dto.enums.Role;
import com.bsuir_finder.dto.enums.TokenType;
import com.bsuir_finder.dto.enums.UserStatus;
import com.bsuir_finder.entity.TokenEntity;
import com.bsuir_finder.entity.UserEntity;
import com.bsuir_finder.mapper.UserMapper;
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
    private final UserValidationService userValidationService;
    private final UserMapper mapper;

    public CustomUserDetailsService(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenService tokenService, EmailService emailService, UserMapper mapper, UserValidationService userValidationService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
        this.emailService = emailService;
        this.userValidationService = userValidationService;
        this.mapper = mapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public User registerUser(User userToCreate) {

        /*TODO:
        restrictions for userToCreate

        checks:
        email, username, enabled(), UserStatus
         */

        userValidationService.validateNewUser(userToCreate);

        String password = passwordEncoder.encode(userToCreate.password());

        var entityToSave = new UserEntity(
                null,
                userToCreate.email(),
                userToCreate.username(),
                password,
                userToCreate.firstName(),
                userToCreate.lastName(),
                Role.USER,
                LocalDate.now(),
                userToCreate.enabled(),
                UserStatus.PENDING
        );

        TokenEntity confirmationToken = new TokenEntity(
                null,
                UUID.randomUUID().toString(),
                null,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                null,
                entityToSave
        );

        tokenService.save(confirmationToken);
        emailService.send(userToCreate.email(), confirmationToken.getToken());

        var userToSave = userRepository.save(entityToSave);
        return mapper.toUser(userToSave);
    }

    public void confirmToken(String token) {
        TokenEntity confirmedToken = tokenService.findToken(token)
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