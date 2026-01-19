package com.bsuir_finder.feature.repository;

import com.bsuir_finder.feature.entity.Token;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TokenRepository extends CrudRepository<Token, Long> {
    Optional<Token> findByToken(String token);
}
