package com.finder.service;

import com.finder.model.dto.User;
import com.finder.model.dto.enums.FormStatus;
import com.finder.model.dto.enums.Role;
import com.finder.model.dto.enums.UserStatus;
import com.finder.model.domain.ProfileEntity;
import com.finder.model.domain.TokenEntity;
import com.finder.model.domain.UserEntity;
import com.finder.mapper.UserMapper;
import com.finder.repository.UserRepository;
import com.finder.security.CustomUserDetails;
import com.finder.validation.UserValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final EmailService emailService;
    private final UserValidation userValidationService;
    private final UserMapper mapper;

    public CustomUserDetailsService(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenService tokenService, EmailService emailService, UserMapper mapper, UserValidation userValidationService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
        this.emailService = emailService;
        this.userValidationService = userValidationService;
        this.mapper = mapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(username));

        return new CustomUserDetails(user);
    }

    @Transactional
    public User registerUser(User userToCreate) {
        log.info("Called registerUser");

        userValidationService.validateNewUser(userToCreate);

        String password = passwordEncoder.encode(userToCreate.password());

        // TODO: Builder
        var entityToSave = new UserEntity(
                null,
                userToCreate.email(),
                userToCreate.username(),
                password,
                Role.USER,
                LocalDate.now(),
                false,
                UserStatus.PENDING,
                null
        );

        var profileToSave = new ProfileEntity(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                FormStatus.HIDDEN,
                false
        );

        var confirmationToken = new TokenEntity(
                null,
                UUID.randomUUID().toString(),
                null,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                null,
                entityToSave
        );

        entityToSave.setProfile(profileToSave);
        profileToSave.setUser(entityToSave);

        var userToSave = userRepository.save(entityToSave);

        tokenService.save(confirmationToken);
        emailService.send(userToCreate.email(), confirmationToken.getToken());

        return mapper.toDto(userToSave);
    }

    public void confirmToken(String token) {
        log.info("Called confirmToken");

        TokenEntity confirmedToken = tokenService.findToken(token)
                .orElseThrow(() ->
                        new IllegalArgumentException("Token not found"));

        if (confirmedToken.getConfirmedAt() != null) {
            throw new IllegalArgumentException("User already verified");
        }

        if (confirmedToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Token expired");
        }

        confirmedToken.setConfirmedAt(LocalDateTime.now());
        tokenService.save(confirmedToken);
        approvedUser(confirmedToken.getUser());
        enableUser(confirmedToken.getUser());
    }

    private void enableUser(UserEntity user) {
        user.setEnabled(true);
        userRepository.save(user);
    }

    private void approvedUser(UserEntity user) {
        user.setUserStatus(UserStatus.APPROVED);
    }
}