package com.bsuir_finder.feature.service;

import com.bsuir_finder.feature.entity.Token;
import com.bsuir_finder.feature.entity.UserInfoEntity;
import com.bsuir_finder.feature.repository.UserDRepository;
import org.springframework.cglib.core.Local;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CustomUserDetailsService implements UserDetailsService {


    private final UserDRepository userDRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public CustomUserDetailsService(UserDRepository userDRepository, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.userDRepository = userDRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public void registerUser(UserInfoEntity user) {
        // check in database if the user with same email address
        // or username already exist

        userDRepository.findByUsernameOrEmail(user.getUsername(), user.getEmail())
                .ifPresent(
                        existingUser -> {
                            throw new IllegalStateException("User already exist");
                        }
                );

        // if a user with the username or email address doesn't exist
        // then save the user
        // firstly encode the password

        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        userDRepository.save(user);

        // send an email with validation link with token
        // user click the form to confirm
        // after confirmation user start being enabled

        Token confirmationToken = new Token(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
        );
        tokenService.save(confirmationToken);

        System.out.println(confirmationToken.getToken());

    }

    public void confirmToken(String token) {
        Token confirmedToken = tokenService.findByToken(token)
                .orElseThrow(
                        () -> new IllegalArgumentException("Token not found")
                );
        if (confirmedToken.getConfirmedAt() != null) {
            throw new IllegalArgumentException("User already verified");
        }

        LocalDateTime expiresAt = confirmedToken.getExpiresAt();
        if (expiresAt.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Token expired");
        }

        confirmedToken.setConfirmedAt(LocalDateTime.now());
        tokenService.save(confirmedToken);
        enableUser(confirmedToken.getUser());
    }

    private void enableUser(UserInfoEntity user) {
        user.setEnabled(true);
        userDRepository.save(user);
    }
}