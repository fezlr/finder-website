package com.bsuir_finder.mapper;

import com.bsuir_finder.dto.User;
import com.bsuir_finder.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toUser(UserEntity userEntity) {
        return new User(
                userEntity.getId(),
                userEntity.getEmail(),
                userEntity.getPassword(),
                userEntity.getRole(),
                userEntity.getCreatedAt(),
                userEntity.getUserStatus()
        );
    }

    public UserEntity toUserEntity(User user) {
        return new UserEntity(
                user.id(),
                user.email(),
                user.password(),
                user.role(),
                user.createdAt(),
                user.userStatus()
        );
    }
}