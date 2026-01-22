package com.bsuir_finder.service;

import com.bsuir_finder.dto.Token;
import com.bsuir_finder.entity.TokenEntity;
import com.bsuir_finder.mapper.TokenMapper;
import com.bsuir_finder.repository.TokenRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TokenService {

    private final TokenRepository tokenRepository;
    private final TokenMapper mapper;

    public TokenService(TokenRepository tokenRepository, TokenMapper mapper) {
        this.tokenRepository = tokenRepository;
        this.mapper = mapper;
    }

    public Optional<TokenEntity> findToken(String token) {
        return tokenRepository.findByToken(token);
    }

    public TokenEntity save(TokenEntity token) {
        return tokenRepository.save(token);
    }
}