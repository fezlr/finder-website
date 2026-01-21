package com.bsuir_finder.mapper;

import com.bsuir_finder.dto.User;
import com.bsuir_finder.entity.UserEntity;
import org.springframework.stereotype.Component;

// id email username firstName lastName password role createdAt enabled userStatus

@Component
public class UserMapper {
    public User toUser(UserEntity userEntity) {
        return new User(
                userEntity.getId(),
                userEntity.getEmail(),
                userEntity.getUsername(),
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.getPassword(),
                userEntity.getRole(),
                userEntity.getCreatedAt(),
                userEntity.getEnabled(),
                userEntity.getUserStatus()
        );
    }

    public UserEntity toUserEntity(User user) {
        return new UserEntity(
                user.id(),
                user.email(),
                user.username(),
                user.firstName(),
                user.lastName(),
                user.password(),
                user.role(),
                user.createdAt(),
                user.enabled(),
                user.userStatus()
        );
    }
}