package com.bsuir_finder.service;

import com.bsuir_finder.entity.TokenEntity;
import com.bsuir_finder.repository.TokenRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TokenService {

    private final TokenRepository tokenRepository;

    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public Optional<TokenEntity> findByToken(String token) {
        return tokenRepository.findByToken(token);
    }

    public TokenEntity save(TokenEntity tokenEntity) {
        return tokenRepository.save(tokenEntity);
    }
}