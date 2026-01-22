package com.bsuir_finder.mapper;

import com.bsuir_finder.dto.Token;
import com.bsuir_finder.entity.TokenEntity;
import org.springframework.stereotype.Component;

@Component
public class TokenMapper {

    private final UserMapper mapper;

    public TokenMapper(UserMapper mapper) {
        this.mapper = mapper;
    }

    public Token toToken(TokenEntity tokenEntity) {
        return new Token(
                tokenEntity.getId(),
                tokenEntity.getToken(),
                tokenEntity.getTokenType(),
                tokenEntity.getCreatedAt(),
                tokenEntity.getExpiresAt(),
                tokenEntity.getConfirmedAt(),
                mapper.toUser(tokenEntity.getUser())
        );

    }

    public TokenEntity toTokenEntity(Token token) {
        return new TokenEntity(
                token.id(),
                token.token(),
                token.tokenType(),
                token.createdAt(),
                token.expiresAt(),
                token.confirmedAt(),
                mapper.toUserEntity(token.user())
        );
    }

}