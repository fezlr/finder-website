package com.bsuir_finder.service;

import com.bsuir_finder.dto.User;
import com.bsuir_finder.dto.enums.Role;
import com.bsuir_finder.dto.enums.UserStatus;
import com.bsuir_finder.entity.UserEntity;
import com.bsuir_finder.mapper.UserMapper;
import com.bsuir_finder.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository repository;
    private final UserMapper mapper;

    public UserService(UserMapper mapper, UserRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    public User findUserById(Long id) {
        UserEntity entity = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Transaction not found with id: " + id)
        );
        return mapper.toUser(entity);
    }

    public List<User> findAllUsers() {
        List<UserEntity> allEntities = repository.findAll();
        return allEntities
                .stream()
                .map(mapper::toUser)
                .toList();
    }

    @Transactional
    public User createUser(User userToCreate) {
        if(userToCreate.id() != null) {
            throw new IllegalArgumentException("Id should be empty");
        }
        if(userToCreate.email() == null) {
            throw new IllegalArgumentException("Email should not be empty");
        }
        if(userToCreate.password() == null) {
            throw new IllegalArgumentException("Password should not be empty");
        }

        var entityToSave = new UserEntity(
                null,
                userToCreate.email(),
                userToCreate.password(),
                Role.USER,
                userToCreate.createdAt(),
                userToCreate.userStatus()
        );

        var userToSave = repository.save(entityToSave);
        return mapper.toUser(userToSave);
    }

    @Transactional
    public User updateUserById(Long id, User userToUpdate) {
        var user = repository
                .findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("User not found with id: " + id)
                );

        if(userToUpdate.email() == null) {
            throw new IllegalArgumentException("Email should not be empty");
        }
        if(userToUpdate.password() == null) {
            throw new IllegalArgumentException("Password should not be empty");
        }
        if(userToUpdate.role() == null) {
            throw new IllegalArgumentException("Role should not be empty");
        }
        if(userToUpdate.userStatus() == null) {
            throw new IllegalArgumentException("User status should not be empty");
        }


        user.setEmail(userToUpdate.email());
        user.setPassword(userToUpdate.password());
        user.setRole(userToUpdate.role());  // Cannot be updated if user is not an admin
        user.setUserStatus(userToUpdate.userStatus());  // Cannot be updated if user is not an admin

        return mapper.toUser(repository.save(user));
    }

    @Transactional
    public void cancelUserById(Long id) {
        var user = repository
                .findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Transaction not found with id: " + id)
                );
        if(user.getUserStatus() == UserStatus.CANCELLED) {
            throw new IllegalArgumentException("User status is already cancelled");
        }
        if(user.getUserStatus() == UserStatus.APPROVED) {
            throw new IllegalArgumentException("User status is already approved");
        }

        repository.setStatus(UserStatus.CANCELLED, id);
        log.info("Successfully canceled transaction with id: {}", id);
    }
}