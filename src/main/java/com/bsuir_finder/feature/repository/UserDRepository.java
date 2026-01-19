package com.bsuir_finder.feature.repository;

import com.bsuir_finder.feature.entity.UserInfoEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserDRepository extends CrudRepository<UserInfoEntity, Long> {
    Optional<UserInfoEntity> findByUsername(String username);
    Optional<UserInfoEntity> findByUsernameOrEmail(String username, String email);
}
