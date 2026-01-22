package com.bsuir_finder.repository;

import com.bsuir_finder.dto.enums.UserStatus;
import com.bsuir_finder.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Modifying
    @Query("""
            UPDATE UserEntity e
            SET e.userStatus = :userStatus
            WHERE e.id = :id
            """)
    void setStatus(
            @Param("userStatus") UserStatus userStatus,
            @Param("id") Long id
    );

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByUsernameOrEmail(String username, String email);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}