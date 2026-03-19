package com.finder.service;

import com.finder.model.domain.TokenEntity;
import com.finder.repository.TokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TokenService {
    private final TokenRepository tokenRepository;

    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public Optional<TokenEntity> findToken(String token) {
        return tokenRepository.findByToken(token);
    }

    @Transactional
    public TokenEntity save(TokenEntity token) {
        return tokenRepository.save(token);
    }
}