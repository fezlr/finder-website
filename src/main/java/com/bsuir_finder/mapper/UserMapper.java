package com.bsuir_finder.mapper;

import com.bsuir_finder.model.dto.User;
import com.bsuir_finder.model.domain.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toDto(UserEntity userEntity) {
        return new User(
                userEntity.getId(),
                userEntity.getEmail(),
                userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.getRole(),
                userEntity.getCreatedAt(),
                userEntity.getUserStatus()
        );
    }
}