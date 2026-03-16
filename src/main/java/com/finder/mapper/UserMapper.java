package com.finder.mapper;

import com.finder.model.dto.User;
import com.finder.model.domain.UserEntity;
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